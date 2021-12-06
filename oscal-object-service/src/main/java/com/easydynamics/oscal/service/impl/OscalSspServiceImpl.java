package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.data.model.OscalSspObject;
import com.easydynamics.oscal.service.OscalSspService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Object service for OSCAL SSPs.
 */
@Service
public class OscalSspServiceImpl
    extends BaseOscalObjectServiceImpl<OscalSspObject>
    implements OscalSspService {

  @Autowired(required = true)
  public OscalSspServiceImpl(
      CrudRepository<OscalSspObject, String> sspRepository
  ) {
    super(sspRepository);
  }

}
