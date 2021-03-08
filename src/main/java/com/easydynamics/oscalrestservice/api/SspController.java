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
 * Ssp Controller for OSCAL REST Service.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class SspController {

  private RestTemplate restTemplate = new RestTemplate();

  private static final String SSP_EXAMPLE_URL = "https://raw.githubusercontent.com/usnistgov/oscal-content/master/examples/ssp/json/ssp-example.json";

  public static final String SSP_EXAMPLE_ID = "66c2a1c8-5830-48bd-8fdd-55a1c3a52888";

  private String sspFromUrl = restTemplate.getForObject(SSP_EXAMPLE_URL, String.class);

  /**
   * Defines a GET request for ssp by ID.
   *
   * @param id the ssp uuid
   * @return the oscal-content ssp-example hosted on github
   */

  @GetMapping("/ssps/{id}")
  public ResponseEntity<String> findById(@Parameter @PathVariable String id) {

    if (id.contains(SSP_EXAMPLE_ID)) {
      return new ResponseEntity<String>(sspFromUrl, HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("Ssp not found", HttpStatus.NOT_FOUND);
    }
  }

}