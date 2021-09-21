package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.ComponentControllerTests.EXAMPLE_COMPONENT_ID;

import com.easydynamics.oscalrestservice.repository.OscalComponentRepository;
import org.junit.jupiter.api.Test;

/**
 * OscalComponentRepositoryTests runs tests of the OscalComponentRepository class.
 */
public class OscalComponentRepositoryTests extends OscalRepositoryTests {
  
  private static final OscalComponentRepository repository = new OscalComponentRepository("oscal-content/components");

  /**
   * Runs the OscalRepositoryTests superclass tests.
   */
  @Test
  public void superTestsComponentRepository() {
    super.nullFindById(repository);
    super.badIdOptionalEmpty(repository);
    super.getGoodId(repository, EXAMPLE_COMPONENT_ID, "\"8223d65f-57a9-4689-8f06-2a975ae2ad72\"");
  }

}
