package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.SspControllerTests.SSP_EXAMPLE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.easydynamics.oscalrestservice.model.OscalSspObject;
import com.easydynamics.oscalrestservice.repository.OscalSspRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class OscalSspRepositoryTests {
  
  private static final OscalSspRepository repository = new OscalSspRepository("oscal-content/system-security-plans");

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
    Optional<OscalSspObject> object = repository.findById(SSP_EXAMPLE_ID);
    assertTrue(object.get().getContent().contains("\"uuid\": \"cff8385f-108e-40a5-8f7a-82f3dc0eaba8\""));
  }


}
