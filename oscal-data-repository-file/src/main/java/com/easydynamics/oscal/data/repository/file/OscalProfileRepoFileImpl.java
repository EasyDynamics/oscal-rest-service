package com.easydynamics.oscal.data.repository.file;

import gov.nist.secauto.oscal.lib.model.Profile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Repository;

/**
 * A repository that handles persistence to files representing OSCAL
 * Profile objects.
 */
@PropertySource("classpath:application.properties")
@Repository
public class OscalProfileRepoFileImpl
    extends BaseOscalRepoFileImpl<Profile> {

  /**
   * Constructs an OscalProfileRepository.
   *
   * @param path path to the directory containing OSCAL Profile files
   */
  public OscalProfileRepoFileImpl(@Value("${persistence.file.profiles.path}") String path) {
    super(path, Profile.class);
  }
}
