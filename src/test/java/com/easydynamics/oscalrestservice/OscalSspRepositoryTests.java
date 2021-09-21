package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.SspControllerTests.SSP_EXAMPLE_ID;

import com.easydynamics.oscalrestservice.repository.OscalSspRepository;
import org.junit.jupiter.api.Test;

/**
 * OscalSspRepositoryTests runs tests of the OscalSspRepository class.
 */
public class OscalSspRepositoryTests extends OscalRepositoryTests {
  
  private static final OscalSspRepository repository = new OscalSspRepository("oscal-content/system-security-plans");

  /**
   * Runs the OscalRepositoryTests superclass tests.
   */
  @Test
  public void superTestsSspRepository() {
    super.nullFindById(repository);
    super.badIdOptionalEmpty(repository);
    super.getGoodId(repository, SSP_EXAMPLE_ID, "\"cff8385f-108e-40a5-8f7a-82f3dc0eaba8\"");
  }

}
