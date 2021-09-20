package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalProfileObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * OscalProfileRepository is a repository that processes requests to the /profiles endpoint. It 
 * is used by the OscalProfileController for the processing of the request.
 */
@PropertySource("classpath:application.properties")
@Service("profileRepository")
public class OscalProfileRepository extends OscalRepository<OscalProfileObject> {

  /**
   * Constructs an OscalProfileRepository.
   * 
   * @param path path to the directory containing OSCAL Profile files
   */
  public OscalProfileRepository(@Value("${persistence.file.profiles.path}") String path) {
    super(path, OscalProfileObject.class);
  }
}
