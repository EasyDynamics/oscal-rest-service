package com.easydynamics.oscal.data.marshalling.impl;

import com.easydynamics.oscal.data.marshalling.IterableAssemblyClassBinding;
import com.easydynamics.oscal.data.marshalling.IterableJsonSerializer;
import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.data.marshalling.OscalObjectMarshallingException;
import gov.nist.secauto.metaschema.binding.IBindingContext;
import gov.nist.secauto.metaschema.binding.io.DeserializationFeature;
import gov.nist.secauto.metaschema.binding.io.IDeserializer;
import gov.nist.secauto.metaschema.binding.io.SerializationFeature;
import gov.nist.secauto.metaschema.binding.io.json.DefaultJsonDeserializer;
import gov.nist.secauto.metaschema.binding.model.IAssemblyClassBinding;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * OscalObjectMarshaller implementation that uses liboscal-java Serializer.
 *
 * @param <T> the OSCAL object type
 */
public abstract class BaseOscalObjectMarshallerLiboscalImpl<T> implements OscalObjectMarshaller<T> {

  private final IterableJsonSerializer<T> serializer;
  private final IDeserializer<T> deserializer;

  /**
   * Constructor for BaseOscalObjectMarshallerLiboscalImpl.
   *
   * @param clazz the OSCAL object class
   */
  public BaseOscalObjectMarshallerLiboscalImpl(Class<T> clazz) {
    super();
    IBindingContext context = IBindingContext.instance();
    IAssemblyClassBinding classBinding = 
        IterableAssemblyClassBinding.createInstance(clazz, context);
    this.serializer = new IterableJsonSerializer<T>(context, classBinding);
    this.serializer.enableFeature(SerializationFeature.SERIALIZE_ROOT);
    this.deserializer = new DefaultJsonDeserializer<T>(context, classBinding);
    this.deserializer.enableFeature(DeserializationFeature.DESERIALIZE_JSON_ROOT_PROPERTY);
  }

  @Override
  public void toJson(T oscalObject, OutputStream outputStream) {
    try {
      serializer.serialize(oscalObject, outputStream);
    } catch (IOException e) {
      throw new OscalObjectMarshallingException(e);
    }
  }

  @Override
  public void toJson(Iterable<T> oscalObjects, OutputStream outputStream) {
    try {
      serializer.serializeIterable(oscalObjects, outputStream);
    } catch (IOException e) {
      throw new OscalObjectMarshallingException(e);
    }
  }

  @Override
  public T toObject(InputStream inputStream) {
    try {
      return deserializer.deserialize(inputStream, null);
    } catch (IOException e) {
      throw new OscalObjectMarshallingException(e);
    }
  }

}
