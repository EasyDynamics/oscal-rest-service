package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.model.OscalSspObject;
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
    extends BaseOscalRepoFilePassImpl<OscalSspObject> {

  /**
   * Constructs an OscalSspRepository.
   *
   * @param path path to the directory containing OSCAL Ssp files
   */
  public OscalSspRepoFilePassImpl(@Value("${persistence.file.ssps.path}") String path) {
    super(path, OscalSspObject.class);
  }
}
