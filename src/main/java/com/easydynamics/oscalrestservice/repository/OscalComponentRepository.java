package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalComponentObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * OscalComponentRepository is a repository that processes requests to the /components endpoint.
 * It is used by the OscalComponentController for the processing of the request.
 */
@PropertySource("classpath:application.properties")
@Service("componentRepository")
public class OscalComponentRepository extends OscalRepository<OscalComponentObject> {
  
  /**
   * Constructs an OscalComponentRepository.
   * 
   * @param path path to the directory containing OSCAL Component files
   * @param genericClass runtime class of OscalComponentObject
   */
  public OscalComponentRepository(@Value("${persistence.file.components.path}") String path) {
    super(path, OscalComponentObject.class);
  }
}
