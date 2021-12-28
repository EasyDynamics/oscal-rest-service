package com.easydynamics.oscal.data.marshalling.impl;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.data.marshalling.OscalObjectMarshallingException;
import gov.nist.secauto.metaschema.binding.BindingContext;
import gov.nist.secauto.metaschema.binding.io.BindingException;
import gov.nist.secauto.metaschema.binding.io.Deserializer;
import gov.nist.secauto.metaschema.binding.io.Feature;
import gov.nist.secauto.metaschema.binding.io.Format;
import gov.nist.secauto.metaschema.binding.io.MutableConfiguration;
import gov.nist.secauto.metaschema.binding.io.Serializer;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * OscalObjectMarshaller implementation that uses liboscal-java Serializer.
 * @param <T> the OSCAL object type
 */
public abstract class BaseOscalObjectMarshallerLiboscalImpl<T> implements OscalObjectMarshaller<T> {

  private final Serializer<T> serializer;
  private final Deserializer<T> deserializer;

  /**
   * Constructor for BaseOscalObjectMarshallerLiboscalImpl.
   * @param clazz the OSCAL object class
   */
  public BaseOscalObjectMarshallerLiboscalImpl(Class<T> clazz) {
    super();
    BindingContext context = BindingContext.newInstance();
    MutableConfiguration config = new MutableConfiguration().enableFeature(
            Feature.SERIALIZE_ROOT).enableFeature(Feature.DESERIALIZE_ROOT);
    this.serializer = context.newSerializer(Format.JSON, clazz, config);
    this.deserializer = context.newDeserializer(Format.JSON, clazz, config);
  }

  @Override
  public void toJson(T oscalObject, OutputStream outputStream) {
    try {
      serializer.serialize(oscalObject, outputStream);
    } catch (BindingException e) {
      throw new OscalObjectMarshallingException(e);
    }
  }

  @Override
  public T toObject(InputStream inputStream) {
    try {
      return deserializer.deserialize(inputStream);
    } catch (BindingException e) {
      throw new OscalObjectMarshallingException(e);
    }
  }

}
