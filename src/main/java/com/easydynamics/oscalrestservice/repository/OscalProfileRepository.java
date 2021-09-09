package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalProfileObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:application.properties")
@Service("profileRepository")
public class OscalProfileRepository extends OscalRepository<OscalProfileObject> {
  
  public OscalProfileRepository(@Value("${persistence.file.profiles.path}") String path) {
    super(path, OscalProfileObject.class);
  }
}
