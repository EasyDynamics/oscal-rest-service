package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.OscalProfileService;
import gov.nist.secauto.oscal.lib.model.Profile;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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


  /**
   * Defines a GET request for profile by ID.
   *
   * @param id the profile uuid
   * @return the oscal-content profile hosted on github
   */

  @GetMapping("/profiles/{id}")
  public ResponseEntity<StreamingResponseBody> findById(@Parameter @PathVariable String id) {
    return super.findById(id);
  }

}
