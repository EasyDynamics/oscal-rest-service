package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalSspObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

@PropertySource("classpath:application.properties")
@Service("sspRepository")
public class OscalSspRepository extends OscalRepository<OscalSspObject> {

  public OscalSspRepository(@Value("${persistence.file.ssps.path}") String path) {
    super(path, OscalSspObject.class);
  }
}
