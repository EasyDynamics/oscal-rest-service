package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.service.OscalSspService;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Ssp Controller for OSCAL REST Service. This class handles all requests to the /ssps endpoint.
 */
@RequestMapping(path = "/oscal/v1")
@RestController
public class SspController extends BaseOscalController<SystemSecurityPlan> {

  @Autowired(required = true)
  public SspController(
      OscalSspService sspService
  ) {
    super(sspService);
  }

  /**
   * Defines a GET request for ssp by ID.
   *
   * @param id the ssp uuid
   * @return the oscal-content ssp-example hosted on github
   */

  @GetMapping("/ssps/{id}")
  public SystemSecurityPlan findById(@Parameter @PathVariable String id) {
    return super.findById(id);
  }

}