package com.easydynamics.oscal.data.marshalling;

import com.fasterxml.jackson.core.JsonGenerator;
import gov.nist.secauto.metaschema.binding.BindingContext;
import gov.nist.secauto.metaschema.binding.io.json.JsonWritingContext;
import gov.nist.secauto.metaschema.binding.model.DefaultAssemblyClassBinding;
import java.io.IOException;

/**
 * Extends DefaultAssemblyClassBinding to add writing of root items as an array.
 *
 */
public class IterableAssemblyClassBinding extends DefaultAssemblyClassBinding {

  protected IterableAssemblyClassBinding(Class<?> clazz, BindingContext bindingContext) {
    super(clazz, bindingContext);
  }

  public static IterableAssemblyClassBinding createInstance(
      Class<?> clazz, BindingContext bindingContext) {
    IterableAssemblyClassBinding retval = new IterableAssemblyClassBinding(clazz, bindingContext);
    return retval;
  }

  /**
   * Writes the given iterable objects as an array.
   *
   * @param instances the iterable objects
   * @param context the JSON writing context
   * @throws IOException when data can't be written
   */
  public void writeRootItems(
      Iterable<?> instances, JsonWritingContext context)
          throws IOException {

    JsonGenerator writer = context.getWriter();

    writer.writeStartArray();

    for (Object instance : instances) {
      writer.writeStartObject();

      writer.writeFieldName(getRootJsonName());

      writeInternal(instance, true, context);

      writer.writeEndObject();
    }

    writer.writeEndArray();
  }
}
