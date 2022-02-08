package com.easydynamics.oscal.data.repository.file;

import gov.nist.secauto.metaschema.binding.BindingContext;
import gov.nist.secauto.metaschema.binding.io.BindingException;
import gov.nist.secauto.metaschema.binding.io.Feature;
import gov.nist.secauto.metaschema.binding.io.Format;
import gov.nist.secauto.metaschema.binding.io.MutableConfiguration;
import gov.nist.secauto.metaschema.binding.io.Serializer;
import gov.nist.secauto.metaschema.binding.model.annotations.MetaschemaAssembly;
import gov.nist.secauto.oscal.java.OscalLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;
import java.util.regex.Pattern;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles persistence to files representing an OSCAL object, which can be a
 * Catalog, Component, Profile, or System Security Plan. Through an implementation of
 * CrudRepository, this class can count, create, delete, edit, find, and save those files.
 */
@Repository
public abstract class BaseOscalRepoFileImpl<T extends Object>
    implements CrudRepository<T, String> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static final String REGEX_UUID =
      "^[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}$";
  private static final Pattern PATTERN_REGEX = Pattern.compile(REGEX_UUID);

  private final Class<T> genericClass;
  private final String path;
  private final String oscalRootName;
  private final OscalLoader oscalLoader;
  private final Serializer<T> serializer;

  /**
   * Constructs an OscalRepository.
   *
   * @param path path to the directory containing files whose content represents an object of type
   *             T extends OscalObject.
   * @param genericClass runtime class of the generic class T, which extends OscalObject
   */
  protected BaseOscalRepoFileImpl(String path, Class<T> genericClass) {
    this.path = path;
    this.genericClass = genericClass;
    this.oscalRootName = getOscalRootName(genericClass);
    this.oscalLoader = new OscalLoader();
    BindingContext context = BindingContext.newInstance();
    MutableConfiguration config = new MutableConfiguration().enableFeature(
            Feature.SERIALIZE_ROOT).enableFeature(Feature.DESERIALIZE_ROOT);
    this.serializer = context.newSerializer(Format.JSON, genericClass, config);
  }

  protected String getOscalRootName(Class<T> genericClass) {
    if (genericClass == null) {
      throw new IllegalArgumentException("genericClass must not be null");
    }
    MetaschemaAssembly metaschemaAssembly =
        genericClass.getAnnotation(MetaschemaAssembly.class);
    if (metaschemaAssembly == null) {
      throw new IllegalArgumentException("genericClass is not a MetaschemaAssembly");
    }
    return metaschemaAssembly.rootName();
  }

  protected File[] getValidatedPathContents() {
    File pathFile = new File(path);
    if (!pathFile.exists()) {
      logger.info("configured path {} does not exist", path);
      return new File[]{};
    }
    File[] pathContents = pathFile.listFiles();
    if (pathContents == null) {
      throw new DataRetrievalFailureException("The provided path is not a directory.");
    }
    return pathContents;
  }

  protected Optional<Path> getValidatedPathToOscalFile(String id) {
    if (id == null) {
      throw new IllegalArgumentException("File id not provided.");
    }

    /*
    * Search through each file in the directory containing files of a specified
    * OSCAL type. If that file's uuid matches the requested uuid, then we return
    * the path to that file.
    */
    try {
      File[] pathContents = getValidatedPathContents();
      for (File f : pathContents) {
        if (PATTERN_REGEX.matcher(id).matches()) {
          try {
            String oscalFileContents = Files.readString(f.toPath(), StandardCharsets.UTF_8);
            String uuid = new JSONObject(oscalFileContents)
                .getJSONObject(oscalRootName).getString("uuid");
            if (uuid.equals(id)) {
              return Optional.of(f.toPath());
            }
          } catch (JSONException e) {
            logger.debug("Unparsable content found at {}", f.getPath());
          } catch (OutOfMemoryError e) {
            throw new DataRetrievalFailureException("Could not load Oscal object.", e);
          } catch (SecurityException e) {
            throw new DataRetrievalFailureException("Could not access file.", e);
          }
        } else {
          if (id.equals(f.getName())) {
            return Optional.of(f.toPath());
          }
        }
      }
      return Optional.empty();
    } catch (InvalidPathException e) {
      throw new DataRetrievalFailureException("Illegal path provided.", e);
    } catch (IOException e) {
      throw new DataRetrievalFailureException("Error obtaining file contents.", e);
    }
  }

  protected Optional<Path> getValidatedPathToOscalFile(T oscalObject) {
    if (oscalObject == null) {
      throw new IllegalArgumentException("oscalObject must not be null");
    }
    try {
      Method getUuid = oscalObject.getClass().getMethod("getUuid");
      UUID uuid = (UUID) getUuid.invoke(oscalObject);
      return getValidatedPathToOscalFile(uuid.toString());
    } catch (IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      throw new InvalidDataAccessResourceUsageException(
          "could not get object UUID", e);
    }
  }

  @Override
  public Optional<T> findById(String id) {
    if (id == null) {
      throw new IllegalArgumentException("File id not provided.");
    }

    Optional<Path> pathToOscalFile = getValidatedPathToOscalFile(id);
    if (pathToOscalFile.isEmpty()) {
      return Optional.empty();
    }

    try {
      return Optional.of(oscalLoader.load(genericClass, new File(pathToOscalFile.get().toUri())));
    } catch (IOException | BindingException e) {
      throw new DataRetrievalFailureException("Failure in loading Oscal object.", e);
    } catch (OutOfMemoryError e) {
      throw new DataRetrievalFailureException("Could not load Oscal object.", e);
    } catch (SecurityException e) {
      throw new DataRetrievalFailureException("Could not access file.", e);
    }
  }

  public long count() {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  public void delete(T entity) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  public void deleteAll() {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  public void deleteAll(Iterable<? extends T> entities) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  public void deleteAllById(Iterable<? extends String> ids) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  public void deleteById(String id) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  public boolean existsById(String id) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  /**
   * Loads OSCAL objects from the validated file path for T object type.
   */
  public Iterable<T> findAll() {
    File[] pathContents = getValidatedPathContents();
    ArrayList<T> foundObjects = new ArrayList<>(pathContents.length);
    for (int i = 0; i < pathContents.length; i++) {
      File filePath = pathContents[i];
      try {
        T foundObject = oscalLoader.load(genericClass, filePath);
        foundObjects.add(foundObject);
      } catch (UnsupportedOperationException e) {
        logger.debug("Unparsable content found at {}", filePath);
      } catch (FileNotFoundException | BindingException e) {
        throw new DataRetrievalFailureException("Failure in loading Oscal object.", e);
      }
    }
    return foundObjects;
  }

  public Iterable<T> findAllById(Iterable<String> ids) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  @Override
  public <S extends T> S save(S entity) {
    if (entity == null) {
      throw new IllegalArgumentException("entity must not be null");
    }
    Optional<Path> pathToOscalFile = getValidatedPathToOscalFile(entity);
    File oscalFile = new File(pathToOscalFile.get().toUri());

    try {
      if (pathToOscalFile.isEmpty()) {
        oscalFile.createNewFile();
      }
      logger.debug("Serializing {} to path {}",
          entity.getClass().getSimpleName(), pathToOscalFile.get().toString());
      serializer.serialize(entity, new File(pathToOscalFile.get().toUri()));
      return entity;
    } catch (BindingException | IOException e) {
      throw new InvalidDataAccessResourceUsageException("Could not serialize to file", e);
    }
  }

  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

}