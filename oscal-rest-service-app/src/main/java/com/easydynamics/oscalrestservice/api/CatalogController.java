package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.OscalCatalogService;
import gov.nist.secauto.oscal.lib.model.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * Catalog Controller for OSCAL REST Service. This class handles all requests to the /catalogs
 * endpoint.
 */
@RequestMapping(path = "/oscal/v1")
@RestController
public class CatalogController extends BaseOscalController<Catalog> {

  @Autowired(required = true)
  public CatalogController(
      OscalCatalogService catalogService,
      OscalObjectMarshaller<Catalog> marshaller
  ) {
    super(catalogService, marshaller);
  }

  @GetMapping("/catalogs")
  public ResponseEntity<StreamingResponseBody> findAll() {
    return super.findAll();
  }

  /**
   * Defines a GET request for catalog by ID.
   *
   * @param id the catalog uuid
   * @return the oscal-content catalog hosted on github
   */

  @GetMapping("/catalogs/{id}")
  public ResponseEntity<StreamingResponseBody> findById(@PathVariable String id) {
    return super.findById(id);
  }

  /**
   * Defines a PATCH request for updating catalogs.
   *
   * @param id the catalog uuid
   * @param json the catalog contents
   */
  @PatchMapping("/catalogs/{id}")
  public ResponseEntity<StreamingResponseBody> patch(
      @PathVariable String id,
      @RequestBody String json) {
    return super.patch(id, json);
  }

  /**
   * Defines a PUT request for updating catalogs.
   *
   * @param id the catalog uuid
   * @param json the catalog contents
   */
  @PutMapping(value = "/catalogs/{id}",
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> put(
      @PathVariable String id,
      @RequestBody String json) {
    return super.put(id, json);
  }
}
