package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.BaseOscalObjectService;
import java.io.ByteArrayInputStream;
import java.io.OutputStream;
import java.util.UUID;
import java.util.function.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * BaseOscalController is the superclass for all Controllers that handles request to an Oscal
 * endpoint. It uses its repository field to perform CRUD operations on files of the corresponding
 * Oscal type.
 */
public abstract class BaseOscalController<T> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  protected final BaseOscalObjectService<T> oscalObjectService;
  protected final OscalObjectMarshaller<T> oscalObjectMarshaller;

  protected BaseOscalController(
      BaseOscalObjectService<T> oscalObjectService,
      OscalObjectMarshaller<T> oscalObjectMarshaller
  ) {
    this.oscalObjectService = oscalObjectService;
    this.oscalObjectMarshaller = oscalObjectMarshaller;
  }

  /**
   * Defines how a get request is handled by an Oscal controller.
   *
   * @param id uuid of the file to open
   * @return HTTP response containing file contents, error message and
   *     status code returned if file cannot be opened.
   */
  public ResponseEntity<StreamingResponseBody> findById(String id) {
    T oscalObject = oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    return makeObjectResponse(oscalObject);
  }

  /**
   * Checks that the given id matches the UUID in the given json.
   *
   * @param id the request path id
   * @param json the request body json
   * @return the unmarshalled object
   * @throws OscalObjectConflictException when the path ID does not match the body ID
   */
  protected T unmarshallAndValidateId(String id, String json) {
    T incomingOscalObject = oscalObjectMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    UUID incomingUuid = oscalObjectService.getUuid(incomingOscalObject);
    if (incomingUuid != null && !id.equals(incomingUuid.toString())) {
      throw new OscalObjectConflictException(incomingUuid.toString(), id);
    }

    return incomingOscalObject;
  }

  /**
   * Defines how a get request is handled by an Oscal controller.
   *
   * @param id uuid of the file to open
   * @return HTTP response containing file contents, error message and
   *     status code returned if file cannot be opened.
   */
  public ResponseEntity<StreamingResponseBody> patch(String id, String json) {
    T incomingOscalObject = unmarshallAndValidateId(id, json);

    T existingOscalObject = oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    T updatedOscalObject = oscalObjectService.merge(incomingOscalObject, existingOscalObject);

    logger.debug("{} merge complete, saving via service",
        updatedOscalObject.getClass().getSimpleName());

    return makeObjectResponse(oscalObjectService.save(updatedOscalObject));
  }

  /**
   * Delegates a find all of the T object type to the repository implementation.
   *
   * @return HTTP response containing OSCAL objects
   */
  public ResponseEntity<StreamingResponseBody> findAll() {
    return makeIterableResponse(oscalObjectService.findAll());
  }

  /**
   * Replaces the OSCAL object of type T with the specified JSON.
   *
   * @param id uuid of the file to open.
   * @param json JSON representation of the object that will replace
   *     the existing contents.
   * @return HTTP response containing OSCAL objects
   */
  public ResponseEntity<StreamingResponseBody> put(String id, String json) {
    T incomingOscalObject = unmarshallAndValidateId(id, json);

    if (!oscalObjectService.existsById(id)) {
      throw new OscalObjectNotFoundException(id);
    }

    return makeObjectResponse(oscalObjectService.save(incomingOscalObject));
  }

  /**
   * Marshall an iterable collection of OSCAL objects to a response object.
   *
   * <p>In particular, this is useful for secondary objects. For root objects in the OSCAL
   * hierarchy (and especially for objects of {@code <T>}), consider using
   * {@link #makeIterableResponse(Iterable)}.
   *
   * @param <S> the type of OSCAL objects within the collection
   * @param oscalObjectCollection the collection of OSCAL objects
   * @param marshaller the marshaller that can convert objects of {@code <S>} to JSON
   * @return HTTP response with the objects as JSON
   */
  protected <S> ResponseEntity<StreamingResponseBody> makeIterableResponse(
      Iterable<S> oscalObjectCollection, OscalObjectMarshaller<S> marshaller) {
    return makeResponse((outputStream) -> marshaller.toJson(oscalObjectCollection, outputStream),
        oscalObjectCollection.getClass());
  }

  /**
   * Marshall an iterable collection of OSCAL objects to a response object.
   *
   * @param oscalObjectCollection The collection of OSCAL objects
   * @return HTTP response with the objects as JSON
   */
  protected ResponseEntity<StreamingResponseBody> makeIterableResponse(
      Iterable<T> oscalObjectCollection) {
    return makeIterableResponse(oscalObjectCollection, oscalObjectMarshaller);
  }

  /**
   * Marshall single JSON object to an HTTP response.
   *
   * <p>In particular, this is useful for secondary objects. For root objects in the OSCAL
   * hierarchy (and especially for objects of {@code <T>}), consider using
   * {@link #makeObjectResponse(T)}.
   *
   * @param <S> the type of the OSCAL object to return
   * @param oscalObject the object to include in the response
   * @param marshaller the marshaller that can convert objects of {@code <S>} to JSON
   * @return HTTP response with the given object as JSON
   */
  protected <S> ResponseEntity<StreamingResponseBody> makeObjectResponse(S oscalObject,
      OscalObjectMarshaller<S> marshaller) {
    return makeResponse((outputStream) -> marshaller.toJson(oscalObject, outputStream),
        oscalObject.getClass());
  }

  /**
   * Marshall single JSON object to an HTTP response.
   *
   * @param oscalObject the object to include in the response
   * @return HTTP response with the given object as JSON
   */
  protected ResponseEntity<StreamingResponseBody> makeObjectResponse(T oscalObject) {
    return makeObjectResponse(oscalObject, oscalObjectMarshaller);
  }

  /**
   * Create a Response object from any class given a valid marshaller to do so.
   *
   * @param marshallingTask the Consumer that will perform the marshalling
   * @param clazz the Class object for the object being marshalled
   */
  protected ResponseEntity<StreamingResponseBody> makeResponse(
      Consumer<OutputStream> marshallingTask, Class<?> clazz) {

    StreamingResponseBody responseBody = outputStream -> {
      logger.debug("Starting marshalling of object type: {}", clazz.getName());
      marshallingTask.accept(outputStream);
      logger.debug("Marshalling complete");
    };

    logger.debug("Returning wrapped response of type: {}", clazz.getName());

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(responseBody);
  }
}
