package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.OscalSspService;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;
import io.swagger.v3.oas.annotations.Parameter;
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
 * Ssp Controller for OSCAL REST Service.
 * This class handles all requests to the /system-security-plans endpoint.
 */
@RequestMapping(path = "/oscal/v1")
@RestController
public class SspController extends BaseOscalController<SystemSecurityPlan> {

  @Autowired(required = true)
  public SspController(
      OscalSspService sspService,
      OscalObjectMarshaller<SystemSecurityPlan> marshaller
  ) {
    super(sspService, marshaller);
  }

  @GetMapping("/system-security-plans")
  public ResponseEntity<StreamingResponseBody> findAll() {
    return super.findAll();
  }

  /**
   * Defines a GET request for ssp by ID.
   *
   * @param id the ssp uuid
   * @return the oscal-content ssp-example hosted on github
   */
  @GetMapping("/system-security-plans/{id}")
  public ResponseEntity<StreamingResponseBody> findById(@Parameter @PathVariable String id) {
    return super.findById(id);
  }

  /**
   * Defines a PATCH request for updating SSPs.
   *
   * @param id the SSP uuid
   * @param json the SSP contents
   */
  @PatchMapping("/system-security-plans/{id}")
  public ResponseEntity<StreamingResponseBody> patch(
      @Parameter @PathVariable String id,
      @RequestBody String json) {
    return super.patch(id, json);
  }

  /**
   * Defines a PUT request for updating SSPs.
   *
   * @param id the SSP uuid
   * @param json the SSP contents
   */
  @PutMapping(value = "/system-security-plans/{id}",
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> put(
      @Parameter @PathVariable String id,
      @RequestBody String json) {
    return super.put(id, json);
  }
}