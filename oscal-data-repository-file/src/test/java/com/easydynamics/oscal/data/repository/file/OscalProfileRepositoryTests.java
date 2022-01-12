package com.easydynamics.oscal.data.repository.file;

import org.junit.jupiter.api.Disabled;

import com.easydynamics.oscal.data.example.ExampleContentConstants;
import gov.nist.secauto.oscal.lib.model.Profile;

/**
 * OscalProfileRepositoryTests runs tests of the OscalProfileRepository class.
 */
@Disabled // See https://github.com/usnistgov/liboscal-java/issues/7
public class OscalProfileRepositoryTests extends BaseOscalRepositoryTests<Profile> {

  private OscalProfileRepositoryTests() {
    this.defaultId = ExampleContentConstants.EXAMPLE_PROFILE_ID;
  }

}
