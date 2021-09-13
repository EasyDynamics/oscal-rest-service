package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscalrestservice.model.OscalObject;
import com.easydynamics.oscalrestservice.repository.OscalRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class OscalController {

  private final OscalRepository<? extends OscalObject> repository;

  protected OscalController(OscalRepository<? extends OscalObject> repository) {
    this.repository = repository;
  }

  /**
   * Defines how a get request is handled by an Oscal controller.
   * 
   * @param id uuid of the file to open
   * @return HTTP response containing file contents, error message and 
   *     status code returned if file cannot be opened.
   */
  public ResponseEntity<String> findById(String id) {
    try {
      return this.repository.findById(id).map(this::buildResponse)
          .orElse(new ResponseEntity<>("Oscal object with ID " + id + " was not found", 
          HttpStatus.NOT_FOUND));
    } catch (DataRetrievalFailureException e) {
      return new ResponseEntity<String>("Something went wrong, could not retrieve the file.",
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  private ResponseEntity<String> buildResponse(OscalObject oscalObject) {
    return new ResponseEntity<>(oscalObject.getContent(), HttpStatus.OK);
  }
}
