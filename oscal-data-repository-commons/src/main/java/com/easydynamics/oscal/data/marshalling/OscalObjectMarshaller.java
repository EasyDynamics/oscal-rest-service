package com.easydynamics.oscal.data.marshalling;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * OscalObjectMarshaller defines an interface to marshall and unmarshall
 * OSCAL objects to and from different formats.
 */
public interface OscalObjectMarshaller<T> {

  /**
   * Serializes the given OSCAL object into JSON on the given output stream.
   *
   * @param oscalObject the OSCAL object
   * @param outputStream the output stream to write to
   */
  public void toJson(T oscalObject, OutputStream outputStream);

  /**
   * Serializes the given OSCAL objects into JSON on the given output stream.
   *
   * @param oscalObjects the iterable OSCAL objects
   * @param outputStream the outputstream to write to
   */
  public void toJson(Iterable<T> oscalObjects, OutputStream outputStream);

  /**
   * Deserializes the given input stream into an OSCAL POJO object.
   *
   * @param inputStream the input stream to deserialize
   * @return the OSCAL object
   */
  public T toObject(InputStream inputStream);
}