package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.model.OscalProfileObject;
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
    extends BaseOscalRepoFilePassImpl<OscalProfileObject> {

  /**
   * Constructs an OscalProfileRepository.
   *
   * @param path path to the directory containing OSCAL Profile files
   */
  public OscalProfileRepoFilePassImpl(@Value("${persistence.file.profiles.path}") String path) {
    super(path, OscalProfileObject.class);
  }
}
