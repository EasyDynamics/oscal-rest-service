package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.data.model.OscalComponentObject;
import com.easydynamics.oscal.service.OscalComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Object service for OSCAL components.
 */
@Service
public class OscalComponentServiceImpl
    extends BaseOscalObjectServiceImpl<OscalComponentObject>
    implements OscalComponentService {

  @Autowired(required = true)
  public OscalComponentServiceImpl(
      CrudRepository<OscalComponentObject, String> componentRepository
  ) {
    super(componentRepository);
  }

}
