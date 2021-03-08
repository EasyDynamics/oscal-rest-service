package com.easydynamics.oscalrestservice.api;


import io.swagger.v3.oas.annotations.Parameter;
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

  private static final String COMPONENT_URL_80053r5 = "https://raw.githubusercontent.com/usnistgov/oscal-content/master/examples/component-definition/json/example-component.json";

  public static final String COMPONENT_ID_80053r5 = "aabcfa61-c6eb-4979-851f-35b461f6a0ef";

  private String componentFromUrl = restTemplate.getForObject(COMPONENT_URL_80053r5, String.class);

  /**
   * Defines a GET request for component by ID.
   *
   * @param id the component-definition uuid
   * @return the oscal-content component hosted on github
   */

  @GetMapping("/components/{id}")
  public ResponseEntity<String> findById(@Parameter @PathVariable String id) {

    if (id.contains(COMPONENT_ID_80053r5)) {
      return new ResponseEntity<String>(componentFromUrl, HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("Component not found", HttpStatus.NOT_FOUND);
    }
  }

}
