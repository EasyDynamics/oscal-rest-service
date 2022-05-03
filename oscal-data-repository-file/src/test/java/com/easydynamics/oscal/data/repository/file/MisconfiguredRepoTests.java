package com.easydynamics.oscal.data.repository.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import gov.nist.secauto.oscal.lib.model.Catalog;

/**
 * MisconfiguredRepoTests runs tests against a misconfigured repository instance.
 */
public class MisconfiguredRepoTests {

  protected CrudRepository<Catalog, String> repository;

  /**
   * Tests that no exception is thrown when path points to
   * a non-existent or empty directory.
   */
  @Test
  public void findByIdDirectoryEmptyOrDoesNotExist() {
    repository = new OscalCatalogRepoFileImpl("non-existent-path");
    Iterable<Catalog> oscalObjects = repository.findAll();
    assertNotNull(oscalObjects);
    assertEquals(0, StreamSupport.stream(oscalObjects.spliterator(), false).count());
  }

  /**
   * Tests that the proper exception is thrown when path points to file.
   */
  @Test
  public void findByIdPathIsFile() {
    repository = new OscalCatalogRepoFileImpl("target/oscal-demo-content-main/README.md");
    assertThrows(DataRetrievalFailureException.class, () -> repository.findById("any-id"));
  }
}
