package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalCatalogObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:application.properties")
@Service("catalogRepository")
public class OscalCatalogRepository extends OscalRepository<OscalCatalogObject> {
  
  public OscalCatalogRepository(@Value("${persistence.file.catalogs.path}") String path) {
    super(path, OscalCatalogObject.class);
  }
}
