package com.easydynamics.oscalrestservice.repository;

import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * OscalSspRepository is a repository that handles persistence to files representing OSCAL SSP
 * objects.
 */
@PropertySource("classpath:application.properties")
@Service("sspRepository")
public class OscalSspRepository extends OscalRepository<SystemSecurityPlan> {

  /**
   * Constructs an OscalSspRepository.
   * 
   * @param path path to the directory containing OSCAL Ssp files
   */
  public OscalSspRepository(@Value("${persistence.file.ssps.path}") String path) {
    super(path, SystemSecurityPlan.class);
  }
}
