package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscalrestservice.repository.OscalRepository;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * OscalController is the superclass for all Controllers that handles request to an Oscal
 * endpoint. It uses its repository field to perform CRUD operations on files of the
 * corresponding Oscal type.
 */
public class OscalController {

  private final OscalRepository<? extends Object> repository;

  protected OscalController(OscalRepository<? extends Object> repository) {
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
      return this.repository.findById(id)
          .map((oscalObject) -> new ResponseEntity<>(
            oscalObject.toString(), HttpStatus.OK))
          .orElse(new ResponseEntity<>("Oscal object with ID " + id + " was not found", 
          HttpStatus.NOT_FOUND));
    } catch (DataRetrievalFailureException e) {
      return new ResponseEntity<String>("Something went wrong, could not retrieve the file.",
        HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
