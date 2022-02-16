package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.repository.ResourceContentRepo;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Catalog Controller for OSCAL REST Service. This class handles all requests to the /catalogs
 * endpoint.
 */
@RequestMapping(path = "/oscal/v1")
@RestController
public class ResourceController {

  private ResourceContentRepo repository;

  @Autowired(required = true)
  public ResourceController(
      ResourceContentRepo repository
  ) {
    this.repository = repository;
  }

  /**
   * Defines a GET request for catalog by ID.
   *
   * @param id the catalog uuid
   * @return the oscal-content catalog hosted on github
   * @throws IOException thrown if there is an issue determining content length
   */
  @GetMapping("/resource-content/{id}")
  public ResponseEntity<Resource> findById(@Parameter @PathVariable String id) throws IOException {
    Resource resource = repository.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    return ResponseEntity.ok()
    //.headers(headers)
    .contentLength(resource.contentLength())
    .contentType(MediaType.APPLICATION_OCTET_STREAM) // TODO - improve this
    .body(resource);
  }

}
