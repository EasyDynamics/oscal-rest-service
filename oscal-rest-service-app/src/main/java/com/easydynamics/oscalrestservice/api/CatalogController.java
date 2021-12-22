package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.service.OscalCatalogService;
import gov.nist.secauto.oscal.lib.model.Catalog;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
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
public class CatalogController extends BaseOscalController<Catalog> {

  @Autowired(required = true)
  public CatalogController(
      OscalCatalogService catalogService
  ) {
    super(catalogService);
  }

  /**
   * Defines a GET request for catalog by ID.
   *
   * @param id the catalog uuid
   * @return the oscal-content catalog hosted on github
   */

  @GetMapping("/catalogs/{id}")
  public Catalog findById(@Parameter @PathVariable String id) {
    return super.findById(id);
  }

}
