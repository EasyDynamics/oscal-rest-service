package com.easydynamics.oscal.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import gov.nist.secauto.metaschema.model.common.datatype.markup.MarkupLine;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class for merging/deep copying OSCAL objects and JSON.
 */
public class OscalDeepCopyUtils {

  /**
   * Leverages Apache bean utils to perform a deep copy of object properties.
   *
   * <p>Note that in this implementation certain object types must be preserved
   * by the caller.  For example, in Lists of objects where equivalence can
   * not be easily determined, the caller must preserve the original list
   * and update/remove/add elements it wants changed.
   *
   * @param dest the destination object
   * @param orig the original object
   * @throws IllegalAccessException if the caller does not have access to the
   *     property accessor method
   * @throws InvocationTargetException if the property accessor method throws an exception
   */
  public static void deepCopyProperties(final Object dest, final Object orig)
      throws IllegalAccessException, InvocationTargetException {

    final Logger logger = LoggerFactory.getLogger(OscalDeepCopyUtils.class);
    final List<Object> objectStack = new ArrayList<>();
    final List<Class<?>> simpleClasses = Arrays.asList(
        MarkupLine.class
    );
    final List<String> simpleClassPackagePrefixes = Arrays.asList(
        "java.", "javax.", "com.sun.", "javax.", "oracle."
    );

    BeanUtilsBean deepCopyBeanUtils = new BeanUtilsBean() {
      @Override
      public void copyProperties(Object dest, Object orig)
          throws IllegalAccessException, InvocationTargetException {
        try {
          if (objectStack.stream().anyMatch(stackObject -> stackObject == dest)) {
            logger.trace("object already in stack, skipping to avoid infinite recursion");
            return;
          }
          objectStack.add(dest);
          super.copyProperties(dest, orig);
        } finally {
          objectStack.remove(dest);
        }
      }

      protected boolean isDeepCopyNeeded(Object value) {
        if (value.getClass().isPrimitive() || value.getClass().isSynthetic()) {
          return false;
        }
        if (simpleClasses.contains(value.getClass())) {
          return false;
        }
        String className = value.getClass().getName();
        for (String simpleClassPackagePrefix : simpleClassPackagePrefixes) {
          if (className.startsWith(simpleClassPackagePrefix)) {
            return false;
          }
        }
        return true;
      }

      @Override
      public void copyProperty(Object dest, String name, Object value)
              throws IllegalAccessException, InvocationTargetException {
        if (value == null || (value instanceof List<?> && ((List<?>) value).isEmpty())) {
          logger.trace("skipping null/empty value for {}", name);
          return;
        }
        if (isDeepCopyNeeded(value)) {
          logger.trace("starting deep copy on {}", name);
          try {
            Object destPropValue = super.getPropertyUtils().getProperty(dest, name);
            if (destPropValue == null) {
              logger.trace("cloning {} value into null destination property", name);
              super.setProperty(dest, name, super.cloneBean(value));
            } else {
              logger.trace("deep copying properties for {}", name);
              copyProperties(destPropValue, value);
            }
          } catch (NoSuchMethodException | InstantiationException e) {
            throw new InvocationTargetException(e);
          }
        } else {
          logger.trace("no deep copy needed for {}", name);
          super.copyProperty(dest, name, value);
        }
      }
    };

    deepCopyBeanUtils.copyProperties(dest, orig);
  }

  /**
   * Leverages Jackson to perform a deep copy of object properties.
   *
   * <p>Note that in this implementation certain object types will be blindly
   * merged.  For example, in Lists of objects where equivalence can
   * not be easily determined, the incoming elements will simply be merged
   * with existing elements, often resulting in duplicates.
   *
   * @param dest the destination JSON string
   * @param orig the original JSON  string
   * @throws JsonProcessingException if the given JSON could not be processed
   * @throws JsonMappingException if the JSON can not be mapped
   */
  public static String deepCopyPropertiesJson(final String dest, final String orig)
      throws JsonMappingException, JsonProcessingException {
    ObjectMapper mapper = new ObjectMapper();
    JsonNode targetNode = mapper.readTree(dest);

    ObjectReader reader = mapper.readerForUpdating(targetNode);

    JsonNode mergedObject = reader.readValue(orig);
    return mergedObject.toString();
  }

}
