package com.easydynamics.oscal.data.repository.filepassimpl;

import com.easydynamics.oscal.data.model.OscalComponentObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

/**
 * A repository that handles persistence to files representing OSCAL
 * Component objects.
 */
@PropertySource("classpath:application.properties")
@Service("componentRepository")
public class OscalComponentRepoFilePassImpl
    extends BaseOscalRepoFilePassImpl<OscalComponentObject> {

  /**
   * Constructs an OscalComponentRepository.
   *
   * @param path path to the directory containing OSCAL Component files
   */
  public OscalComponentRepoFilePassImpl(@Value("${persistence.file.components.path}") String path) {
    super(path, OscalComponentObject.class);
  }
}
