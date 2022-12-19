package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.OscalProfileService;
import gov.nist.secauto.oscal.lib.model.Profile;
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
 * Profile Controller for OSCAL REST Service. This class handles all requests to the /profiles
 * endpoint.
 */

@RequestMapping(path = "/oscal/v1")
@RestController
public class ProfilesController extends BaseOscalController<Profile> {

  @Autowired(required = true)
  public ProfilesController(
      OscalProfileService profileService,
      OscalObjectMarshaller<Profile> marshaller
  ) {
    super(profileService, marshaller);
  }

  @GetMapping("/profiles")
  public ResponseEntity<StreamingResponseBody> findAll() {
    return super.findAll();
  }

  /**
   * Defines a GET request for profile by ID.
   *
   * @param id the profile uuid
   * @return the oscal-content profile hosted on github
   */

  @GetMapping("/profiles/{id}")
  public ResponseEntity<StreamingResponseBody> findById(@PathVariable String id) {
    return super.findById(id);
  }

  /**
   * Defines a PATCH request for updating profiles.
   *
   * @param id the profile uuid
   * @param json the profile contents
   */
  @PatchMapping("/profiles/{id}")
  public ResponseEntity<StreamingResponseBody> patch(
      @PathVariable String id,
      @RequestBody String json) {
    return super.patch(id, json);
  }

  /**
   * Defines a PUT request for updating profiles.
   *
   * @param id the profile uuid
   * @param json the profile contents
   */
  @PutMapping(value = "/profiles/{id}",
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> put(
      @PathVariable String id,
      @RequestBody String json) {
    return super.put(id, json);
  }
}
