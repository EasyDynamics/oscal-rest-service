package com.easydynamics.oscalrestservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.easydynamics.oscalrestservice.model.OscalObject;
import com.easydynamics.oscalrestservice.repository.OscalRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

/**
 * OscalRepositoryTests is an abstract class that provides methods for common tests among the
 * classes running tests of the different OscalRepository types.
 */
public abstract class OscalRepositoryTests {
  
  protected OscalRepository<? extends OscalObject> repository;
  protected String defaultId;

  /**
   * Tests if a null id will cause an OSCAL repository to throw an IllegalArgumentException when
   * trying to find a file.
   */
  @Test
  public void nullFindById() {
    assertThrows(IllegalArgumentException.class, () -> repository.findById(null));
  }

  /**
   * Tests if an OscalRepository returns an empty optional object when trying to find a file
   * using an invalid id.
   */
  @Test
  public void badIdOptionalEmpty() {
    assertEquals(Optional.empty(), repository.findById("bad-id"));
  }

  /**
   * Tests if an OscalRepository returns content when trying to find a file using a valid id.
   */
  @Test
  public void getGoodId() {
    assertTrue(!repository.findById(defaultId).get().getContent().isEmpty());
  }
}
