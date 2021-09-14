package com.easydynamics.oscalrestservice;

import static com.easydynamics.oscalrestservice.CatalogControllerTest.CATALOG_ID_80053r5;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.easydynamics.oscalrestservice.model.OscalCatalogObject;
import com.easydynamics.oscalrestservice.repository.OscalCatalogRepository;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class OscalCatalogRepositoryTests {
  
  private static final OscalCatalogRepository repository = new OscalCatalogRepository("oscal-content/catalogs");

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
    Optional<OscalCatalogObject> object = repository.findById(CATALOG_ID_80053r5);
    assertTrue(object.get().getContent().contains("\"uuid\": \"613fca2d-704a-42e7-8e2b-b206fb92b456\""));
  }
}
