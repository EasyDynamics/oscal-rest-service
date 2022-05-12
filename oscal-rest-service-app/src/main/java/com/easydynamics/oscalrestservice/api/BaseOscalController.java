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
 * endpoint. It uses its repository field to perform CRUD operations on files of the
 * corresponding Oscal type.
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

  protected ResponseEntity<StreamingResponseBody> makeIterableResponse(
      Iterable<T> oscalObjectCollection) {
    return makeResponse(
      (outputStream) -> oscalObjectMarshaller.toJson(oscalObjectCollection, outputStream),
      oscalObjectCollection.getClass());
  }

  protected ResponseEntity<StreamingResponseBody> makeObjectResponse(T oscalObject) {
    return makeResponse(
      (outputStream) -> oscalObjectMarshaller.toJson(oscalObject, outputStream),
      oscalObject.getClass());
  }

  protected ResponseEntity<StreamingResponseBody> makeResponse(
      Consumer<OutputStream> marshallingTask,
      Class<?> clazz) {

    StreamingResponseBody responseBody = outputStream -> {
      logger.debug("Starting marshalling of object type: {}",
          clazz.getName());
      marshallingTask.accept(outputStream);
      logger.debug("Marshalling complete");
    };

    logger.debug("Returning wrapped response of type: {}",
        clazz.getName());

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(responseBody);
  }
}
