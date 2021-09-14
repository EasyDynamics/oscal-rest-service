package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.ProfilesControllerTest.EXAMPLE_PROFILE_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.easydynamics.oscalrestservice.model.OscalProfileObject;
import com.easydynamics.oscalrestservice.repository.OscalProfileRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;


public class OscalProfileRepositoryTests {
  
  private static final OscalProfileRepository repository = new OscalProfileRepository("oscal-content/profiles");

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
    Optional<OscalProfileObject> object = repository.findById(EXAMPLE_PROFILE_ID);
    assertTrue(object.get().getContent().contains("\"uuid\": \"8b3beca1-fcdc-43e0-aebb-ffc0a080c486\""));
  }

}
