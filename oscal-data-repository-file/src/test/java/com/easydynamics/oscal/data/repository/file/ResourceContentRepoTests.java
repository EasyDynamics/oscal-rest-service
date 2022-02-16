package com.easydynamics.oscal.data.repository.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.io.IOException;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.easydynamics.oscal.data.repository.ResourceContentRepo;

/**
 * Tests of the ResourceContentRepo implementation
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public class ResourceContentRepoTests {

  private static final String EXPECTED_NAME = "authz-boundary.png";
  private static final Long EXPECTED_LENGTH = 53758L;

  @Autowired
  protected ResourceContentRepo repository;

  /**
   * Tests that resource content can be retrieved.
   */
  @Test
  public void findById() throws IOException {
    Optional<Resource> result = repository.findById(EXPECTED_NAME);
    assertTrue(result.isPresent());
    Resource resource = result.get();
    assertEquals(EXPECTED_NAME, resource.getFilename());
    assertEquals(EXPECTED_LENGTH, resource.contentLength());
    assertNotNull(resource.getInputStream());
  }

}
