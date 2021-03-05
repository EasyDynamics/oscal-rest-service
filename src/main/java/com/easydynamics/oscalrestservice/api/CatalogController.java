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
 * Catalog Controller for OSCAL REST Service
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class CatalogController {

  private RestTemplate restTemplate = new RestTemplate();

  static String urlForCatalog = "https://raw.githubusercontent.com/usnistgov/oscal-content/master/nist.gov/SP800-53/rev5/json/NIST_SP-800-53_rev5_catalog.json";

  private String catalogFromUrl = restTemplate.getForObject(urlForCatalog, String.class);

  /**
   * Defines a GET request for catalog by ID.
   *
   * @param id the catalog uuid
   * @return the oscal-content catalog hosted on github
   */

  @GetMapping("/catalogs/{id}")
  public ResponseEntity<String> findById(@Parameter @PathVariable String id) {

    if (id.contains("62f21617-b40f-4e89-bf3b-01b04b68f473")) {
      return new ResponseEntity<String>(catalogFromUrl, HttpStatus.OK);
    } else {
      return new ResponseEntity<String>("Ssp not found", HttpStatus.BAD_REQUEST);
    }
  }

}
