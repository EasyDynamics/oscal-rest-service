package com.easydynamics.oscalrestservice;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.data.marshalling.impl.OscalBackMatterResourceMarshallerImpl;
import com.easydynamics.oscal.data.marshalling.impl.OscalCatalogMarshallerImpl;
import com.easydynamics.oscal.data.marshalling.impl.OscalComponentMarshallerImpl;
import com.easydynamics.oscal.data.marshalling.impl.OscalProfileMarshallerImpl;
import com.easydynamics.oscal.data.marshalling.impl.OscalSspImplReqMarshallerImpl;
import com.easydynamics.oscal.data.marshalling.impl.OscalSspMarshallerImpl;
import gov.nist.secauto.oscal.lib.model.BackMatter.Resource;
import gov.nist.secauto.oscal.lib.model.Catalog;
import gov.nist.secauto.oscal.lib.model.ComponentDefinition;
import gov.nist.secauto.oscal.lib.model.ImplementedRequirement;
import gov.nist.secauto.oscal.lib.model.Profile;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Spring configuration class for services.
 */
@Configuration
@ComponentScan("com.easydynamics.oscal.service")
public class ServiceConfig {

  @Bean
  public OscalObjectMarshaller<Catalog> catalogMarshaller() {
    return new OscalCatalogMarshallerImpl();
  }

  @Bean
  public OscalObjectMarshaller<ComponentDefinition> componentMarshaller() {
    return new OscalComponentMarshallerImpl();
  }

  @Bean
  public OscalObjectMarshaller<Profile> profileMarshaller() {
    return new OscalProfileMarshallerImpl();
  }

  @Bean
  public OscalObjectMarshaller<SystemSecurityPlan> sspMarshaller() {
    return new OscalSspMarshallerImpl();
  }

  @Bean
  public OscalObjectMarshaller<ImplementedRequirement> sspImplReqMarshaller() {
    return new OscalSspImplReqMarshallerImpl();
  }

  @Bean
  public OscalObjectMarshaller<Resource> backMatterResourceMarshaller() {
    return new OscalBackMatterResourceMarshallerImpl();
  }
}
