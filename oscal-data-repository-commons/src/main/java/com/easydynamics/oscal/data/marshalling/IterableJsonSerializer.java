package com.easydynamics.oscal.data.marshalling;

import com.fasterxml.jackson.core.JsonGenerator;
import gov.nist.secauto.metaschema.binding.IBindingContext;
import gov.nist.secauto.metaschema.binding.io.BindingException;
import gov.nist.secauto.metaschema.binding.io.json.DefaultJsonSerializer;
import gov.nist.secauto.metaschema.binding.io.json.DefaultJsonWritingContext;
import gov.nist.secauto.metaschema.binding.io.json.IJsonWritingContext;
import gov.nist.secauto.metaschema.binding.model.IAssemblyClassBinding;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.jetbrains.annotations.NotNull;

/**
 * Extends DefaultJsonSerializer to serialize root items as an array.
 *
 * @param <T> the OSCAL object type
 */
public class IterableJsonSerializer<T> extends DefaultJsonSerializer<T> {

  public IterableJsonSerializer(IBindingContext bindingContext,
      IAssemblyClassBinding classBinding) {
    super(bindingContext, classBinding);
  }

  public void serializeIterable(Iterable<T> data, OutputStream out) throws IOException {
    serializeIterable(data, new OutputStreamWriter(out));
  }

  /**
   * Serializes the given iterable data to the given writer.
   *
   * @param data the iterable data
   * @param writer the writer to write to
   * @throws BindingException thrown when the class binding fails
   */
  public void serializeIterable(Iterable<T> data, Writer writer) throws IOException {
    try (JsonGenerator generator = newJsonGenerator(writer)) {
      IterableAssemblyClassBinding classBinding = (IterableAssemblyClassBinding) getClassBinding();
      IJsonWritingContext writingContext = new DefaultJsonWritingContext(generator);
      classBinding.writeRootItems(data, writingContext);
      generator.close();
    }
  }

  /**
   * Need to override here to prevent calling writer.flush
   */
  @Override
  public void serialize(@NotNull T data, @NotNull OutputStream os) throws IOException {
    OutputStreamWriter writer = new OutputStreamWriter(os);
    serialize(data, writer);
    // Stream is already closed after
    // RootAssemblyDefinition.writeRoot > WriterBasedJsonGenerator.writeEndObject) returns
    // so below fails with 'stream closed'
    // writer.flush();
  }

}
