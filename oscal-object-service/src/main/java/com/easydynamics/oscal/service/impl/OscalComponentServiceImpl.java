package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.service.OscalComponentService;
import gov.nist.secauto.oscal.lib.model.ComponentDefinition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Object service for OSCAL components.
 */
@Service
public class OscalComponentServiceImpl
    extends BaseOscalObjectServiceImpl<ComponentDefinition>
    implements OscalComponentService {

  @Autowired(required = true)
  public OscalComponentServiceImpl(
      CrudRepository<ComponentDefinition, String> componentRepository
  ) {
    super(componentRepository);
  }

}
