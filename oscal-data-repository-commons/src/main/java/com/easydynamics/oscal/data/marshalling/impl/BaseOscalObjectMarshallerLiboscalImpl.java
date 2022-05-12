package com.easydynamics.oscal.data.marshalling.impl;

import com.easydynamics.oscal.data.marshalling.IterableAssemblyClassBinding;
import com.easydynamics.oscal.data.marshalling.IterableJsonSerializer;
import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.data.marshalling.OscalObjectMarshallingException;
import gov.nist.secauto.metaschema.binding.IBindingContext;
import gov.nist.secauto.metaschema.binding.io.BindingException;
import gov.nist.secauto.metaschema.binding.io.Feature;
import gov.nist.secauto.metaschema.binding.io.IDeserializer;
import gov.nist.secauto.metaschema.binding.io.json.DefaultJsonDeserializer;
import gov.nist.secauto.metaschema.binding.model.IAssemblyClassBinding;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * OscalObjectMarshaller implementation that uses liboscal-java Serializer.
 * @param <T> the OSCAL object type
 */
public abstract class BaseOscalObjectMarshallerLiboscalImpl<T> implements OscalObjectMarshaller<T> {

  private final IterableJsonSerializer<T> serializer;
  private final IDeserializer<T> deserializer;

  /**
   * Constructor for BaseOscalObjectMarshallerLiboscalImpl.
   * @param clazz the OSCAL object class
   */
  public BaseOscalObjectMarshallerLiboscalImpl(Class<T> clazz) {
    super();
    IBindingContext context = IBindingContext.newInstance();
    IAssemblyClassBinding classBinding =
        IterableAssemblyClassBinding.createInstance(clazz, context);
    this.serializer = new IterableJsonSerializer<T>(context, classBinding);
    this.serializer.enableFeature(Feature.SERIALIZE_ROOT);
    this.deserializer = new DefaultJsonDeserializer<T>(context, classBinding);
    this.deserializer.enableFeature(Feature.DESERIALIZE_ROOT);
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
  public void toJson(Iterable<T> oscalObjects, OutputStream outputStream) {
    try {
      serializer.serializeIterable(oscalObjects, outputStream);
    } catch (BindingException e) {
      throw new OscalObjectMarshallingException(e);
    }
  }

  @Override
  public T toObject(InputStream inputStream) {
    try {
      return deserializer.deserialize(inputStream, null);
    } catch (BindingException e) {
      throw new OscalObjectMarshallingException(e);
    }
  }

}
