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
 * Profile Controller for OSCAL REST Service.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class ProfileController {
  private RestTemplate restTemplate = new RestTemplate();

  private static final String EXAMPLE_PROFILE_URL = "https://raw.githubusercontent.com/usnistgov/oscal-content/master/nist.gov/SP800-53/rev4/json/NIST_SP-800-53_rev4_MODERATE-baseline_profile.json";

  public static final String EXAMPLE_PROFILE_ID = "0f2814d7-a9a1-4b1f-aec8-eb7b10c1ef06";

  private String profileFromUrl = restTemplate.getForObject(EXAMPLE_PROFILE_URL, String.class);

  /**
   * Defines a GET request for profile by ID.
   *
   * @param id the profile uuid
   * @return the oscal-content profile hosted on github
   */

  @GetMapping("/profiles/{id}")
  public ResponseEntity<String> findById(@Parameter @PathVariable String id) {

    if (id.contains(EXAMPLE_PROFILE_ID)) {
      return new ResponseEntity<String>(profileFromUrl, HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("Profile not found", HttpStatus.NOT_FOUND);
    }
  }

}