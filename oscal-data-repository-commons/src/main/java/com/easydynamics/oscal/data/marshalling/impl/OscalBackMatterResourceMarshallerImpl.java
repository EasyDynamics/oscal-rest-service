package com.easydynamics.oscal.data.marshalling.impl;

import gov.nist.secauto.oscal.lib.model.BackMatter.Resource;

/**
 * OSCAL BackMatter Resource marshaller implementation.
 */
public class OscalBackMatterResourceMarshallerImpl
    extends BaseOscalObjectMarshallerLiboscalImpl<Resource> {

  public OscalBackMatterResourceMarshallerImpl() {
    super(Resource.class);
  }
}
