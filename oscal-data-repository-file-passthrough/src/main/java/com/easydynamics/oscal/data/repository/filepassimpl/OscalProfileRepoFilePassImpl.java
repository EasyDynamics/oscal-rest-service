package com.easydynamics.oscal.data.repository.filepassimpl;

import gov.nist.secauto.oscal.lib.model.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * A repository that handles persistence to files representing OSCAL
 * Profile objects.
 */
@PropertySource("classpath:application.properties")
@Service("profileRepository")
public class OscalProfileRepoFilePassImpl
    extends BaseOscalRepoFilePassImpl<Profile> {

  /**
   * Constructs an OscalProfileRepository.
   *
   * @param path path to the directory containing OSCAL Profile files
   */
  public OscalProfileRepoFilePassImpl(@Value("${persistence.file.profiles.path}") String path) {
    super(path, Profile.class);
  }
}
