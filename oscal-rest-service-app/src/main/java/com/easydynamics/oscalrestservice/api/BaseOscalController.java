package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.BaseOscalObjectService;
import java.io.ByteArrayInputStream;
import java.util.UUID;
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

    StreamingResponseBody responseBody = outputStream -> {
      logger.debug("Starting marshalling of object type: {}", oscalObject.getClass().getName());
      oscalObjectMarshaller.toJson(oscalObject, outputStream);
      logger.debug("Marshalling complete");
    };

    return ResponseEntity.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(responseBody);
  }

  /**
   * Defines how a get request is handled by an Oscal controller.
   *
   * @param id uuid of the file to open
   * @return HTTP response containing file contents, error message and
   *     status code returned if file cannot be opened.
   */
  public ResponseEntity<StreamingResponseBody> patch(String id, String json) {
    T incomingOscalObject = oscalObjectMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    T existingOscalObject = oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    UUID incomingUuid = oscalObjectService.getUuid(incomingOscalObject);
    if (incomingUuid != null && !id.equals(incomingUuid.toString())) {
      throw new OscalObjectConflictException("object UUID did not match path UUID");
    }

    T updatedOscalObject = oscalObjectService.merge(incomingOscalObject, existingOscalObject);

    logger.debug("{} merge complete, saving via service",
        updatedOscalObject.getClass().getSimpleName());

    oscalObjectService.save(existingOscalObject);

    logger.debug("{} save complete, re-retrieving from service",
        existingOscalObject.getClass().getSimpleName());

    return findById(id);
  }
}
