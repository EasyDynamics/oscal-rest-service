package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalComponentObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:application.properties")
@Service("componentRepository")
public class OscalComponentRepository extends OscalRepository<OscalComponentObject> {
  
  public OscalComponentRepository(@Value("${persistence.file.components.path}") String path) {
    super(path, OscalComponentObject.class);
  }
}
