package com.easydynamics.oscal.data.marshalling.impl;

import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;

/**
 * OSCAL SSP marshaller implementation.
 */
public class OscalSspMarshallerImpl
    extends BaseOscalObjectMarshallerLiboscalImpl<SystemSecurityPlan> {

  public OscalSspMarshallerImpl() {
    super(SystemSecurityPlan.class);
  }

}
