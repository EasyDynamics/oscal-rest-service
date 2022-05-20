package com.easydynamics.oscal.data.marshalling;

import com.fasterxml.jackson.core.JsonGenerator;
import gov.nist.secauto.metaschema.binding.IBindingContext;
import gov.nist.secauto.metaschema.binding.io.json.IJsonWritingContext;
import gov.nist.secauto.metaschema.binding.model.DefaultAssemblyClassBinding;
import gov.nist.secauto.oscal.lib.model.ImplementedRequirement;
import java.io.IOException;
import java.util.Map;
import javax.xml.namespace.QName;

/**
 * Extends DefaultAssemblyClassBinding to add writing of root items as an array.
 */
public class IterableAssemblyClassBinding extends DefaultAssemblyClassBinding {

  /**
   * Map of objects that are not declared as root in liboscal-java,
   * but we want to treat them as such.
   */
  private static final Map<Class<?>, QName> secondaryRootObjects = Map.ofEntries(
      Map.entry(ImplementedRequirement.class, new QName("implemented-requirement"))
  );

  protected IterableAssemblyClassBinding(Class<?> clazz, IBindingContext bindingContext) {
    super(clazz, bindingContext);
  }

  public static IterableAssemblyClassBinding createInstance(
      Class<?> clazz, IBindingContext bindingContext) {
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
      Iterable<?> instances, IJsonWritingContext context)
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

  @Override
  public boolean isRoot() {
    return (super.isRoot() || secondaryRootObjects.containsKey(getBoundClass()));
  }

  @Override
  public QName getRootXmlQName() {
    return secondaryRootObjects.getOrDefault(getBoundClass(), super.getRootXmlQName());
  }

}
