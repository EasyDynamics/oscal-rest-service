package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.ProfilesControllerTest.EXAMPLE_PROFILE_ID;

import com.easydynamics.oscalrestservice.repository.OscalProfileRepository;
import org.junit.jupiter.api.Test;

/**
 * OscalProfileRepositoryTests runs tests of the OscalProfileRepository class.
 */
public class OscalProfileRepositoryTests extends OscalRepositoryTests {
  
  private static final OscalProfileRepository repository = new OscalProfileRepository("oscal-content/profiles");

  /**
   * Runs the OscalRepositoryTests superclass tests.
   */
  @Test
  public void superTestsProfileRepository() {
    super.nullFindById(repository);
    super.badIdOptionalEmpty(repository);
    super.getGoodId(repository, EXAMPLE_PROFILE_ID, "\"8b3beca1-fcdc-43e0-aebb-ffc0a080c486\"");
  }

}
