package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.service.OscalCatalogService;
import gov.nist.secauto.oscal.lib.model.Catalog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Service;

/**
 * Object service for OSCAL catalogs.
 */
@Service
public class OscalCatalogServiceImpl
    extends BaseOscalObjectServiceImpl<Catalog>
    implements OscalCatalogService {

  @Autowired(required = true)
  public OscalCatalogServiceImpl(
      CrudRepository<Catalog, String> catalogRepository
  ) {
    super(catalogRepository);
  }

}
