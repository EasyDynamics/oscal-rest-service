package com.easydynamics.oscalrestservice.api;

import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Component Controller for OSCAL REST Service.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class ComponentController {

  private RestTemplate restTemplate = new RestTemplate();

  private static final String EXAMPLE_COMPONENT_URL = "https://raw.githubusercontent.com/usnistgov/oscal-content/master/examples/component-definition/json/example-component.json";

  public static final String EXAMPLE_COMPONENT_ID = "aabcfa61-c6eb-4979-851f-35b461f6a0ef";

  private String componentFromUrl = restTemplate.getForObject(EXAMPLE_COMPONENT_URL, String.class);

  /**
   * Defines a GET request for component by ID.
   *
   * @param id the component-definition uuid
   * @return the oscal-content component hosted on github
   */

  @GetMapping("/components/{id}")
  public ResponseEntity<String> findById(@Parameter @PathVariable String id) {
    if (id.equals(EXAMPLE_COMPONENT_ID)) {
      return new ResponseEntity<String>(componentFromUrl, HttpStatus.OK);
    }

    String parentDirectory = System.getenv("PARENT_DIR");
    String componentsDirectory = System.getenv("COMPONENTS_DIR");
    String json;

    if (parentDirectory == null || componentsDirectory == null) {
      return new ResponseEntity<String>("Component not found", HttpStatus.NOT_FOUND);
    }

    try {
      json = Files.readString(Path.of(parentDirectory, componentsDirectory, id));
    } catch (IOException e) {
      return new ResponseEntity<String>("Component not found", HttpStatus.NOT_FOUND);
    }
    
    return new ResponseEntity<String>(json, HttpStatus.OK);
  }

}
