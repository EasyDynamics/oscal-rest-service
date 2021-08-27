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
 * Profile Controller for OSCAL REST Service.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class ProfilesController {
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

  /**
   * Defines a GET request for a profile via an environment variable.
   *
   * @param profileLocalJson the environment variable representing the local
   *                         profile file
   * @return the oscal content of the local profile json file
   */
  @GetMapping("/profile/env/{profileLocalJson}")
  public ResponseEntity<String> findByLocalEnv(@Parameter @PathVariable String profileLocalJson) {
    String fileName = System.getenv(profileLocalJson);
    if (fileName == null) {
      return new ResponseEntity<String>("profileLocalJson is not an environemnt variable.", 
        HttpStatus.NOT_FOUND);
    }
    String contents;
    try {
      contents = Files.readString(Path.of(fileName));
    } catch (IOException e) {
      return new ResponseEntity<String>("Profile file does not exist locally.", 
        HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<String>(contents, HttpStatus.OK);
  }

}
