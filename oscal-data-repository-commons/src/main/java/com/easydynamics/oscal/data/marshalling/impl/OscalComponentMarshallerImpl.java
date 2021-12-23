package com.easydynamics.oscal.data.marshalling.impl;

import gov.nist.secauto.oscal.lib.model.ComponentDefinition;

/**
 * OSCAL Component marshaller implementation.
 */
public class OscalComponentMarshallerImpl
    extends BaseOscalObjectMarshallerLiboscalImpl<ComponentDefinition> {

  public OscalComponentMarshallerImpl() {
    super(ComponentDefinition.class);
  }

}
