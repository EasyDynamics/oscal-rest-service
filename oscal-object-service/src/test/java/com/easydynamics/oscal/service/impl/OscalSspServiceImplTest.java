package com.easydynamics.oscal.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.easydynamics.oscal.service.OscalSspService;
import gov.nist.secauto.metaschema.datatypes.markup.MarkupLine;
import gov.nist.secauto.oscal.lib.model.Metadata;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class OscalSspServiceImplTest {

  private static final String EXPECTED_TITLE = "Some New Title";

  @Autowired
  private OscalSspService oscalSspService;

  @Test
  public void testMerge() {
    UUID uuid = UUID.randomUUID();

    SystemSecurityPlan origSsp = new SystemSecurityPlan();
    origSsp.setUuid(uuid);

    MarkupLine origTitle = MarkupLine.fromMarkdown("Some Original Title");
    Metadata origMetadata = new Metadata();
    origMetadata.setTitle(origTitle);
    origMetadata.setVersion("1.0");

    origSsp.setMetadata(origMetadata);

    SystemSecurityPlan newSsp = new SystemSecurityPlan();
    newSsp.setUuid(uuid);

    MarkupLine newTitle = MarkupLine.fromMarkdown(EXPECTED_TITLE);
    Metadata newMetadata = new Metadata();
    newMetadata.setTitle(newTitle);

    newSsp.setMetadata(newMetadata);

    oscalSspService.merge(newSsp, origSsp);

    assertEquals(EXPECTED_TITLE, newSsp.getMetadata().getTitle().toMarkdown());
  }

}
