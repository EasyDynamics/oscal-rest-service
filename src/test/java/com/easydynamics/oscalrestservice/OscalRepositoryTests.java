package com.easydynamics.oscalrestservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.easydynamics.oscalrestservice.model.OscalObject;
import com.easydynamics.oscalrestservice.repository.OscalRepository;
import java.util.Optional;

/**
 * OscalRepositoryTests is an abstract class that provides methods for common tests among the
 * classes running tests of the different OscalRepository types.
 */
public abstract class OscalRepositoryTests {
  
  /**
   * Tests if a null id will cause an OSCAL repository to throw an IllegalArgumentException when
   * trying to find a file.
   * 
   * @param repository OscalRepository that persists to files
   */
  public void nullFindById(OscalRepository<? extends OscalObject> repository) {
    assertThrows(IllegalArgumentException.class, () -> repository.findById(null));
  }

  /**
   * Tests if an OscalRepository returns an empty optional object when trying to find a file
   * using an invalid id.
   * 
   * @param repository OscalRepository that persists to files
   */
  public void badIdOptionalEmpty(OscalRepository<? extends OscalObject> repository) {
    assertEquals(Optional.empty(), repository.findById("bad-id"));
  }

  /**
   * Tests is an OscalRepository returns the correct content when trying to find a file using a
   * valid id.
   * 
   * @param repository OscalRepository that persists to files
   * @param goodId a valid file id
   * @param uuid uuid seen in the file contents, an identifier of the respective OscalObject
   */
  public void getGoodId(OscalRepository<? extends OscalObject> repository, String goodId, String uuid) {
    Optional<? extends OscalObject> object = repository.findById(goodId);
    assertTrue(object.get().getContent().contains("\"uuid\": " + uuid));
  }
}
