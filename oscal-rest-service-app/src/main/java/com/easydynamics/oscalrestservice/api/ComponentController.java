package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.OscalComponentService;
import gov.nist.secauto.oscal.lib.model.ComponentDefinition;
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

  @GetMapping("/component-definitions")
  public ResponseEntity<StreamingResponseBody> findAll() {
    return super.findAll();
  }

  /**
   * Defines a GET request for component by ID.
   *
   * @param id the component-definition uuid
   * @return the oscal-content component hosted on github
   */

  @GetMapping("/component-definitions/{id}")
  public ResponseEntity<StreamingResponseBody> findById(@PathVariable String id) {
    return super.findById(id);
  }

  /**
   * Defines a PATCH request for updating component definitions.
   *
   * @param id the component definition uuid
   * @param json the component definition contents
   */
  @PatchMapping("/component-definitions/{id}")
  public ResponseEntity<StreamingResponseBody> patch(
      @PathVariable String id,
      @RequestBody String json) {
    return super.patch(id, json);
  }

  /**
   * Defines a PUT request for updating component definitions.
   *
   * @param id the component definition uuid
   * @param json the component definition contents
   */
  @PutMapping(value = "/component-definitions/{id}", 
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> put(
      @PathVariable String id,
      @RequestBody String json) {
    return super.put(id, json);
  }
}
