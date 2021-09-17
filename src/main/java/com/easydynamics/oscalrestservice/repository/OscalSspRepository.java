package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalSspObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * OscalSspRepository is a repository that processes requests to the /ssps endpoint. It is used
 * by the OscalSspController for the processing of the request.
 */
@PropertySource("classpath:application.properties")
@Service("sspRepository")
public class OscalSspRepository extends OscalRepository<OscalSspObject> {

  /**
   * Constructs an OscalSspRepository.
   * 
   * @param path path to the directory containing OSCAL Ssp files
   * @param genericClass runtime class of OscalSspObject
   */
  public OscalSspRepository(@Value("${persistence.file.ssps.path}") String path) {
    super(path, OscalSspObject.class);
  }
}
