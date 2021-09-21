package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalSspObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * OscalSspRepository is a repository that handles persistence to files representing OSCAL SSP
 * objects.
 */
@PropertySource("classpath:application.properties")
@Service("sspRepository")
public class OscalSspRepository extends OscalRepository<OscalSspObject> {

  /**
   * Constructs an OscalSspRepository.
   * 
   * @param path path to the directory containing OSCAL Ssp files
   */
  public OscalSspRepository(@Value("${persistence.file.ssps.path}") String path) {
    super(path, OscalSspObject.class);
  }
}
