package com.easydynamics.oscal.data.repository.filepassimpl;

import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * A repository that handles persistence to files representing OSCAL SSP
 * objects.
 */
@PropertySource("classpath:application.properties")
@Service("sspRepository")
public class OscalSspRepoFilePassImpl
    extends BaseOscalRepoFilePassImpl<SystemSecurityPlan> {

  /**
   * Constructs an OscalSspRepository.
   *
   * @param path path to the directory containing OSCAL Ssp files
   */
  public OscalSspRepoFilePassImpl(@Value("${persistence.file.ssps.path}") String path) {
    super(path, SystemSecurityPlan.class);
  }
}
