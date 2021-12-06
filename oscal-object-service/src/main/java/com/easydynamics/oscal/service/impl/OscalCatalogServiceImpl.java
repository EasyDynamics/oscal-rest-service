package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.data.model.OscalCatalogObject;
import com.easydynamics.oscal.service.OscalCatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Object service for OSCAL catalogs.
 */
@Service
public class OscalCatalogServiceImpl
    extends BaseOscalObjectServiceImpl<OscalCatalogObject>
    implements OscalCatalogService {

  @Autowired(required = true)
  public OscalCatalogServiceImpl(
      CrudRepository<OscalCatalogObject, String> catalogRepository
  ) {
    super(catalogRepository);
  }

}
