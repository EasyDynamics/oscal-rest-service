package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.service.OscalSspService;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Object service for OSCAL SSPs.
 */
@Service
public class OscalSspServiceImpl
    extends BaseOscalObjectServiceImpl<SystemSecurityPlan>
    implements OscalSspService {

  @Autowired(required = true)
  public OscalSspServiceImpl(
      CrudRepository<SystemSecurityPlan, String> sspRepository
  ) {
    super(sspRepository);
  }

}
