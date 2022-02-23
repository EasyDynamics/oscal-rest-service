package com.easydynamics.oscalrestservice.api;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class OscalObjectConflictExceptionTests {
  
  @Test
  public void testWhenNullIdsDoesNotThrow() throws Exception {
    assertDoesNotThrow(() -> new OscalObjectConflictException(null, null));
  }

  @Test
  public void testReturnsValidMessage() throws Exception {
    String objectId = "oID";
    String pathId = "pID";

    var exception = new OscalObjectConflictException(objectId, pathId);

    assertEquals("object UUID (oID) did not match path UUID (pID)",
        exception.getMessage());
  }
}
