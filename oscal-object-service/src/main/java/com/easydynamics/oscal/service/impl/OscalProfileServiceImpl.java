package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.data.model.OscalProfileObject;
import com.easydynamics.oscal.service.OscalProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Object service for OSCAL profiles.
 */
@Service
public class OscalProfileServiceImpl
    extends BaseOscalObjectServiceImpl<OscalProfileObject>
    implements OscalProfileService {

  @Autowired(required = true)
  public OscalProfileServiceImpl(
      CrudRepository<OscalProfileObject, String> profileRepository
  ) {
    super(profileRepository);
  }

}
