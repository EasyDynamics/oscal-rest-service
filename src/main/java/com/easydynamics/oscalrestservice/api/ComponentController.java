package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscalrestservice.repository.OscalComponentRepository;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Component Controller for OSCAL REST Service. This class handles all requests to the /components
 * endpoint.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class ComponentController extends OscalController {

  @Autowired(required = true)
  public ComponentController(OscalComponentRepository componentRepository) {
    super(componentRepository);
  }

  /**
   * Defines a GET request for component by ID.
   *
   * @param id the component-definition uuid
   * @return the oscal-content component hosted on github
   */

  @GetMapping("/components/{id}")
  public ResponseEntity<String> findById(@Parameter @PathVariable String id) {
    return super.findById(id);
  }

}
