package com.easydynamics.oscal.data.repository.file;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import gov.nist.secauto.oscal.lib.model.Catalog;

/**
 * MisconfiguredRepoTests runs tests against a misconfigured repository instance.
 */
public class MisconfiguredRepoTests {

  protected CrudRepository<Catalog, String> repository;

  @BeforeEach
  public void setUp() {
    repository = new OscalCatalogRepoFileImpl("bad-path");
  }

  /**
   * Tests that the proper exception is thrown
   */
  @Test
  public void nullFindById() {
    assertThrows(DataRetrievalFailureException.class, () -> repository.findById("any-id"));
  }

}
