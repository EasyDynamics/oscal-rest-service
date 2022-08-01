package com.easydynamics.oscal.data.marshalling.impl;

import gov.nist.secauto.oscal.lib.model.Statement;

/**
 * OSCAL Statement marshaller implementation.
 */
public class OscalSspStatementMarshallerImpl
    extends BaseOscalObjectMarshallerLiboscalImpl<Statement> {

  public OscalSspStatementMarshallerImpl() {
    super(Statement.class);
  } 
}
