package com.easydynamics.oscal.data.repository.file;

import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

/**
 * A repository that handles persistence to files representing OSCAL SSP
 * objects.
 */
@PropertySource("classpath:application.properties")
@Repository
public class OscalSspRepoFileImpl
    extends BaseOscalRepoFileImpl<SystemSecurityPlan> {

  /**
   * Constructs an OscalSspRepository.
   *
   * @param path path to the directory containing OSCAL Ssp files
   */
  public OscalSspRepoFileImpl(@Value("${persistence.file.ssps.path}") String path) {
    super(path, SystemSecurityPlan.class);
  }
}
