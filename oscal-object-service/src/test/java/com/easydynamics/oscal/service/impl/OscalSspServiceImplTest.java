package com.easydynamics.oscal.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.OscalSspService;
import gov.nist.secauto.metaschema.model.common.datatype.markup.MarkupLine;
import gov.nist.secauto.oscal.lib.model.Metadata;
import gov.nist.secauto.oscal.lib.model.Metadata.Party;
import gov.nist.secauto.oscal.lib.model.Property;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = ServiceConfig.class)
public class OscalSspServiceImplTest {

  private static final String EXPECTED_TITLE = "Some New Title";
  private static final String EXPECTED_VERSION = "1.0";

  @Autowired
  private OscalSspService oscalSspService;

  @Autowired
  private OscalObjectMarshaller<SystemSecurityPlan> oscalObjectMarshaller;

  @Test
  public void testMerge() {
    UUID uuid = UUID.randomUUID();

    SystemSecurityPlan origSsp = new SystemSecurityPlan();
    origSsp.setUuid(uuid);
    String origTitleString = "Some Original Title";
    MarkupLine origTitle = MarkupLine.fromMarkdown(origTitleString);

    LinkedList<Property> origProps = new LinkedList<>();
    Property origProp1 = new Property();
    origProp1.setName("prop1");
    origProp1.setValue("orig value 1");
    Property origProp2 = new Property();
    origProp2.setName("prop2");
    origProp2.setValue("orig value 2");
    origProps.add(origProp1);
    origProps.add(origProp2);

    LinkedList<Party> origParties = new LinkedList<>();
    Party origParty = new Party();
    UUID origPartyUuid = UUID.randomUUID();
    origParty.setUuid(origPartyUuid);
    origParties.add(origParty);

    Metadata origMetadata = new Metadata();
    origMetadata.setTitle(origTitle);
    origMetadata.setVersion(EXPECTED_VERSION);
    origMetadata.setProps(origProps);
    origMetadata.setParties(origParties);
    origSsp.setMetadata(origMetadata);

    String newSspString = "\n{\n"
        + "  \"system-security-plan\": {\n"
        + "    \"uuid\": \"" + uuid.toString() + "\",\n"
        + "    \"metadata\": {\n"
        + "      \"title\": \"" + EXPECTED_TITLE + "\",\n"
        + "      \"version\": \"1.0\",\n"
        + "      \"props\": [\n"
        + "        {\n"
        + "          \"name\": \"prop1\",\n"
        + "          \"value\": \"orig value 1\"\n"
        + "        },\n"
        + "        {\n"
        + "          \"name\": \"prop2\",\n"
        + "          \"value\": \"new value 2\"\n"
        + "        }\n"
        + "      ]\n"
        + "    }\n"
        + "  }\n"
        + "}\n";

    assertEquals(origTitleString, origSsp.getMetadata().getTitle().toMarkdown());
    assertEquals(EXPECTED_VERSION, origSsp.getMetadata().getVersion());

    InputStream newSspInputStream = new ByteArrayInputStream(newSspString.getBytes());
    SystemSecurityPlan newSsp = oscalObjectMarshaller.toObject(newSspInputStream);

    SystemSecurityPlan mergedSsp = oscalSspService.merge(newSsp, origSsp);
    ByteArrayOutputStream margedOutputStream = new ByteArrayOutputStream();
    oscalObjectMarshaller.toJson(mergedSsp, margedOutputStream);
    String mergedSspJson = margedOutputStream.toString();

    assertEquals(EXPECTED_TITLE, mergedSsp.getMetadata().getTitle().toMarkdown(),
        "The mergedSsp title should have been updated: " + mergedSspJson);
    assertEquals(EXPECTED_VERSION, mergedSsp.getMetadata().getVersion(),
        "The mergedSsp version should not have changed: " + mergedSspJson);
    assertEquals("orig value 1", mergedSsp.getMetadata().getProps().get(0).getValue(),
        "The mergedSsp prop1 should not have changed: " + mergedSspJson);
    assertEquals("new value 2", mergedSsp.getMetadata().getProps().get(1).getValue(),
        "The mergedSsp prop2 should have been updated: " + mergedSspJson);
    assertEquals(origPartyUuid, mergedSsp.getMetadata().getParties().get(0).getUuid(),
        "The mergedSsp parties should not have changed: " + mergedSspJson);
  }

}
