package com.easydynamics.oscal.data.marshalling.impl;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.data.marshalling.OscalObjectMarshallingException;
import gov.nist.secauto.metaschema.binding.BindingContext;
import gov.nist.secauto.metaschema.binding.io.BindingException;
import gov.nist.secauto.metaschema.binding.io.Feature;
import gov.nist.secauto.metaschema.binding.io.Format;
import gov.nist.secauto.metaschema.binding.io.MutableConfiguration;
import gov.nist.secauto.metaschema.binding.io.Serializer;
import java.io.OutputStream;

/**
 * OscalObjectMarshaller implementation that uses liboscal-java Serializer.
 * @param <T> the OSCAL object type
 */
public abstract class BaseOscalObjectMarshallerLiboscalImpl<T> implements OscalObjectMarshaller<T> {

  private final Class<T> clazz;

  public BaseOscalObjectMarshallerLiboscalImpl(Class<T> clazz) {
    super();
    this.clazz = clazz;
  }

  @Override
  public void toJson(T oscalObject, OutputStream outputStream) {
    BindingContext context = BindingContext.newInstance();
    MutableConfiguration config = new MutableConfiguration().enableFeature(
            Feature.SERIALIZE_ROOT).enableFeature(Feature.DESERIALIZE_ROOT);
    Serializer<T> serializer = context.newSerializer(Format.JSON, clazz, config);
    try {
      serializer.serialize(oscalObject, outputStream);
    } catch (BindingException e) {
      throw new OscalObjectMarshallingException(e);
    }
  }

}
