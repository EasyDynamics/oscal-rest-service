package com.easydynamics.oscal.data.repository.filepassimpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.lang.reflect.Method;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.data.repository.CrudRepository;

/**
 * OscalRepositoryTests is an abstract class that provides methods for common tests among the
 * classes running tests of the different OscalRepository types.
 */
public abstract class BaseOscalRepositoryTests<T extends Object> {

  protected CrudRepository<T, String> repository;
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
   * @throws Exception
   */
  @Test
  public void getGoodId() throws Exception {
    Object oscalObject = repository.findById(defaultId).get();
    assertNotNull(oscalObject);
    Method getUuid = oscalObject.getClass().getMethod("getUuid");
    String uuid = (String) getUuid.invoke(oscalObject);

    assertEquals(defaultId, uuid);
  }
}
