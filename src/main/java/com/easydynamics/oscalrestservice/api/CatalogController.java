package com.easydynamics.oscalrestservice.api;

import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * Catalog Controller for OSCAL REST Service.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class CatalogController {

  private RestTemplate restTemplate = new RestTemplate();

  private static final String CATALOG_URL_80053r5 = "https://raw.githubusercontent.com/usnistgov/oscal-content/master/nist.gov/SP800-53/rev5/json/NIST_SP-800-53_rev5_catalog.json";

  public static final String CATALOG_ID_80053r5 = "62f21617-b40f-4e89-bf3b-01b04b68f473";

  private String catalogFromUrl = restTemplate.getForObject(CATALOG_URL_80053r5, String.class);

  @Autowired
  private Environment env;

  /**
   * Defines a GET request for catalog by ID.
   *
   * @param id the catalog uuid
   * @return the oscal-content catalog hosted on github
   */

  @GetMapping("/catalogs/{id}")
  public ResponseEntity<String> findById(@Parameter @PathVariable String id) {

    if (id.contains(CATALOG_ID_80053r5)) {
      return new ResponseEntity<String>(catalogFromUrl, HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("Catalog not found", HttpStatus.NOT_FOUND);
    }
  }

  /**
   * Defines a GET request for a catalog via an environment variable.
   *
   * @param catalogLocalJson the environment variable representing the local
   *                         catalog file
   * @return the oscal content of the local catalog json file
   */
  @GetMapping("/catalogs/env/{catalogLocalJson}")
  public ResponseEntity<String> findByLocalEnv(@Parameter @PathVariable String catalogLocalJson) {
    String fileName = env.getProperty(catalogLocalJson);
    if (fileName == null) {
      return new ResponseEntity<String>("catalogLocalJson is not an environemnt variable.", 
        HttpStatus.NOT_FOUND);
    }
    String contents;
    try {
      contents = Files.readString(Path.of(fileName));
    } catch (IOException e) {
      return new ResponseEntity<String>("Catalog file does not exist locally.", 
        HttpStatus.NOT_FOUND);
    }

    return new ResponseEntity<String>(contents, HttpStatus.OK);
  }

}
