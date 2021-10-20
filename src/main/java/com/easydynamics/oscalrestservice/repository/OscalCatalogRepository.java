package com.easydynamics.oscalrestservice.repository;

import gov.nist.secauto.oscal.lib.model.Catalog;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * OscalCatalogRepository is a repository that handles persistence to files representing OSCAL
 * Catalog objects.
 */
@PropertySource("classpath:application.properties")
@Service("catalogRepository")
public class OscalCatalogRepository extends OscalRepository<Catalog> {
  
  /**
   * Constructs an OscalCatalogRepository.
   * 
   * @param path path to the directory containing OSCAL Catalog files
   */
  public OscalCatalogRepository(@Value("${persistence.file.catalogs.path}") String path) {
    super(path, Catalog.class);
  }
}
