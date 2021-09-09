package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscalrestservice.model.OscalSspObject;
import com.easydynamics.oscalrestservice.repository.OscalSspRepository;
import io.swagger.v3.oas.annotations.Parameter;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ssp Controller for OSCAL REST Service.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class SspController {

  @Autowired
  private OscalSspRepository sspRepository;
  
  /**
   * Defines a GET request for ssp by ID.
   *
   * @param id the ssp uuid
   * @return the oscal-content ssp-example hosted on github
   */

  @GetMapping("/ssps/{id}")
  public ResponseEntity<String> findById(@Parameter @PathVariable String id) {
    Optional<OscalSspObject> response;
    try {
      response = sspRepository.findById(id);
    } catch (Exception e) {
      return new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    if (response.isEmpty()) {
      String responseMessage = "Ssp with id " + id + " was not found";
      return new ResponseEntity<String>(responseMessage, HttpStatus.NOT_FOUND);
    }
    HttpHeaders headers = new HttpHeaders();
    headers.add("Access-Control-Allow-Origin", "localhost");

    return new ResponseEntity<String>(response.get().getContent(), headers, HttpStatus.OK);
  }

}