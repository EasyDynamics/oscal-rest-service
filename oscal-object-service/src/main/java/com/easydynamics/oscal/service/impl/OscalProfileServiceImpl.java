package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.service.OscalProfileService;
import gov.nist.secauto.oscal.lib.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Object service for OSCAL profiles.
 */
@Service
public class OscalProfileServiceImpl
    extends BaseOscalObjectServiceImpl<Profile>
    implements OscalProfileService {

  @Autowired(required = true)
  public OscalProfileServiceImpl(
      CrudRepository<Profile, String> profileRepository
  ) {
    super(profileRepository);
  }

}
