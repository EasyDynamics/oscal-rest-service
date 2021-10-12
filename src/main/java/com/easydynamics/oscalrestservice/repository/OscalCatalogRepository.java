package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalCatalogObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * OscalCatalogRepository is a repository that handles persistence to files representing OSCAL
 * Catalog objects.
 */
@PropertySource("classpath:application.properties")
@Service("catalogRepository")
public class OscalCatalogRepository extends OscalRepository<OscalCatalogObject> {
  
  /**
   * Constructs an OscalCatalogRepository.
   * 
   * @param path path to the directory containing OSCAL Catalog files
   */
  public OscalCatalogRepository(@Value("${persistence.file.catalogs.path}") String path) {
    super(path, OscalCatalogObject.class);
  }
}
