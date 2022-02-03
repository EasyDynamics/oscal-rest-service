package com.easydynamics.oscal.data.repository.file;

import org.junit.jupiter.api.Disabled;

import com.easydynamics.oscal.data.example.ExampleContent;
import gov.nist.secauto.oscal.lib.model.Profile;

/**
 * OscalProfileRepositoryTests runs tests of the OscalProfileRepository class.
 */
@Disabled // See https://github.com/usnistgov/liboscal-java/issues/7
public class OscalProfileRepositoryTests extends BaseOscalRepositoryTests<Profile> {

  private OscalProfileRepositoryTests() {
    this.defaultId = ExampleContent.PROFILE_80053r4MOD.uuid;
  }

}
