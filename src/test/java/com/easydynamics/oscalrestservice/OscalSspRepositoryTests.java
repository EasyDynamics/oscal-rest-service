package com.easydynamics.oscalrestservice;

import java.io.File;

import com.easydynamics.oscalrestservice.repository.OscalSspRepository;

import org.junit.jupiter.api.Test;

import gov.nist.secauto.metaschema.binding.BindingContext;
import gov.nist.secauto.metaschema.binding.io.Feature;
import gov.nist.secauto.metaschema.binding.io.Format;
import gov.nist.secauto.metaschema.binding.io.MutableConfiguration;
import gov.nist.secauto.metaschema.binding.io.Serializer;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;

/**
 * OscalSspRepositoryTests runs tests of the OscalSspRepository class.
 */
public class OscalSspRepositoryTests extends OscalRepositoryTests {

  public static final String SSP_EXAMPLE_ID = "66c2a1c8-5830-48bd-8fdd-55a1c3a52888";
  private static final String EXPECTED_NEW_NAME = "Some New SSP Name";

  private OscalSspRepositoryTests() {
    this.repository = new OscalSspRepository("oscal-content/system-security-plans");
    this.defaultId = SSP_EXAMPLE_ID;
  }

  @Test
  public void testChangeTitle() throws Exception {
    SystemSecurityPlan systemSecurityPlan = (SystemSecurityPlan) repository.findById(defaultId).get();
    systemSecurityPlan.getSystemCharacteristics().setSystemName(EXPECTED_NEW_NAME);
    
    BindingContext context = BindingContext.newInstance();
    MutableConfiguration config
        = new MutableConfiguration().enableFeature(Feature.SERIALIZE_ROOT).enableFeature(Feature.DESERIALIZE_ROOT);
    Serializer<SystemSecurityPlan> serializer = context.newSerializer(Format.JSON, SystemSecurityPlan.class, config);
    File out = new File("target/modified-ssp.json");
    serializer.serialize(systemSecurityPlan, out);
  }
}
