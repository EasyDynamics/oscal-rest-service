package com.easydynamics.oscal.data.marshalling;

import com.fasterxml.jackson.core.JsonGenerator;
import gov.nist.secauto.metaschema.binding.BindingContext;
import gov.nist.secauto.metaschema.binding.io.BindingException;
import gov.nist.secauto.metaschema.binding.io.Configuration;
import gov.nist.secauto.metaschema.binding.io.json.DefaultJsonSerializer;
import gov.nist.secauto.metaschema.binding.io.json.DefaultJsonWritingContext;
import gov.nist.secauto.metaschema.binding.io.json.JsonWritingContext;
import gov.nist.secauto.metaschema.binding.model.AssemblyClassBinding;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

/**
 * Extends DefaultJsonSerializer to serialize root items as an array.
 *
 * @param <T> the OSCAL object type
 */
public class IterableJsonSerializer<T> extends DefaultJsonSerializer<T> {

  public IterableJsonSerializer(BindingContext bindingContext, AssemblyClassBinding classBinding,
      Configuration configuration) {
    super(bindingContext, classBinding, configuration);
  }

  public void serializeIterable(Iterable<T> data, OutputStream out) throws BindingException {
    serializeIterable(data, new OutputStreamWriter(out));
  }

  /**
   * Serializes the given iterable data to the given writer.
   *
   * @param data the iterable data
   * @param writer the writer to write to
   * @throws BindingException thrown when the class binding fails
   */
  public void serializeIterable(Iterable<T> data, Writer writer) throws BindingException {
    JsonGenerator generator = newJsonGenerator(writer);
    try {
      IterableAssemblyClassBinding classBinding = (IterableAssemblyClassBinding) getClassBinding();
      JsonWritingContext writingContext = new DefaultJsonWritingContext(generator);
      classBinding.writeRootItems(data, writingContext);
      generator.close();
    } catch (IOException ex) {
      throw new BindingException(ex);
    }
  }

}
