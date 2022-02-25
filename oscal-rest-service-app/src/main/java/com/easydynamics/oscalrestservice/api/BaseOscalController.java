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

  private final BaseOscalObjectService<T> oscalObjectService;
  private final OscalObjectMarshaller<T> oscalObjectMarshaller;

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

    return makeResponse(oscalObject);
  }

  /**
   * Checks that the given id matches the UUID in the given json.
   *
   * @param id the request path id
   * @param json the request body json
   * @return the unmarshalled object
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

    return makeResponse(oscalObjectService.save(updatedOscalObject));
  }

  /**
   * Delegates a find all of the T object type to the repository implementation.
   *
   * @return HTTP response containing OSCAL objects
   */
  public ResponseEntity<StreamingResponseBody> findAll() {
    return makeResponse(oscalObjectService.findAll());
  }

  /**
   * Replaces the OSCAL object of type T with the specified JSON.
   *
   * @param id uuid of the file to open.
   * @param json JSON representation of the object that will replace
   *     the existing contents.
   * @return HTTP NO_CONTENT response, or error message and
   *     status code returned if file cannot be opened.
   */
  public ResponseEntity<StreamingResponseBody> put(String id, String json) {
    T incomingOscalObject = unmarshallAndValidateId(id, json);

    if (!oscalObjectService.existsById(id)) {
      throw new OscalObjectNotFoundException(id);
    }

    return makeResponse(oscalObjectService.save(incomingOscalObject));
  }

  private ResponseEntity<StreamingResponseBody> makeResponse(Object typeErasedOscalObject) {

    // Need to suppress warnings here because the type cast is considered unsafe.
    // However since it's a private method, and we know when it's going to be used
    // this should be ok.
    @SuppressWarnings("unchecked")
    Consumer<OutputStream> marshallingTask = (typeErasedOscalObject instanceof Iterable)
        ? (outputStream) -> oscalObjectMarshaller.toJson((Iterable<T>)typeErasedOscalObject,
            outputStream)
        : (outputStream) -> oscalObjectMarshaller.toJson((T)typeErasedOscalObject,
            outputStream);

    StreamingResponseBody responseBody = outputStream -> {
      logger.debug("Starting marshalling of object type: {}",
          typeErasedOscalObject.getClass().getName());
      marshallingTask.accept(outputStream);
      logger.debug("Marshalling complete");
    };

    logger.debug("Returning wrapped response of type: {}",
        typeErasedOscalObject.getClass().getName());

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(responseBody);
  }
}
