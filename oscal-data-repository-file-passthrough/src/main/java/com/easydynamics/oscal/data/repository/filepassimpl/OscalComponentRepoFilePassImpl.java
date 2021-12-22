package com.easydynamics.oscal.data.repository.filepassimpl;

import gov.nist.secauto.oscal.lib.model.ComponentDefinition;
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
    extends BaseOscalRepoFilePassImpl<ComponentDefinition> {

  /**
   * Constructs an OscalComponentRepository.
   *
   * @param path path to the directory containing OSCAL Component files
   */
  public OscalComponentRepoFilePassImpl(@Value("${persistence.file.components.path}") String path) {
    super(path, ComponentDefinition.class);
  }
}
