package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.OscalComponentService;
import gov.nist.secauto.oscal.lib.model.ComponentDefinition;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * Component Controller for OSCAL REST Service. This class handles all requests to the /components
 * endpoint.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class ComponentController extends BaseOscalController<ComponentDefinition> {

  @Autowired(required = true)
  public ComponentController(
      OscalComponentService componentService,
      OscalObjectMarshaller<ComponentDefinition> marshaller
  ) {
    super(componentService, marshaller);
  }

  /**
   * Defines a GET request for component by ID.
   *
   * @param id the component-definition uuid
   * @return the oscal-content component hosted on github
   */

  @GetMapping("/component-definitions/{id}")
  public ResponseEntity<StreamingResponseBody> findById(@Parameter @PathVariable String id) {
    return super.findById(id);
  }

}
