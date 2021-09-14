package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.ComponentControllerTests.EXAMPLE_COMPONENT_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.easydynamics.oscalrestservice.model.OscalComponentObject;
import com.easydynamics.oscalrestservice.repository.OscalComponentRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class OscalComponentRepositoryTests {
  
  private static final OscalComponentRepository repository = new OscalComponentRepository("oscal-content/components");

  @Test
  public void nullFindById() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> repository.findById(null));
  }

  @Test
  public void badIdOptionalEmpty() throws Exception {
    assertEquals(Optional.empty(), repository.findById("bad-id"));
  }

  @Test
  public void getGoodId() throws Exception {
    Optional<OscalComponentObject> object = repository.findById(EXAMPLE_COMPONENT_ID);
    assertTrue(object.get().getContent().contains("\"uuid\": \"8223d65f-57a9-4689-8f06-2a975ae2ad72\""));
  }
}
