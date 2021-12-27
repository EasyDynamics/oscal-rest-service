package com.easydynamics.oscal.data.repository.file;

import gov.nist.secauto.metaschema.binding.io.BindingException;
import gov.nist.secauto.oscal.java.OscalLoader;
import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles persistence to files representing an OSCAL object, which can be a
 * Catalog, Component, Profile, or System Security Plan. Through an implementation of
 * CrudRepository, this class can count, create, delete, edit, find, and save those files.
 */
@Repository
public class BaseOscalRepoFileImpl<T extends Object>
    implements CrudRepository<T, String> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private Class<T> genericClass;
  private String path;
  private OscalLoader oscalLoader = new OscalLoader();

  protected BaseOscalRepoFileImpl() {

  }

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
  }

  /**
   * Finds an OSCAL file and returns its contents.
   *
   * @param id the OSCAL file name
   * @return OSCAL contents of the file, if it exists
   */
  public Optional<T> findById(String id) {
    if (id == null) {
      throw new IllegalArgumentException("File id not provided.");
    }

    Path pathToOscalFile;

    try {
      pathToOscalFile = (Path.of(this.path, id + ".json"));
      logger.debug("Chacking pathToOscalFile={}", pathToOscalFile);
      if (!pathToOscalFile.toFile().exists() || pathToOscalFile.toFile().isDirectory()) {
        return Optional.empty();
      }
    } catch (InvalidPathException e) {
      throw new DataRetrievalFailureException("Illegal path provided.", e);
    }

    try {
      return Optional.of(oscalLoader.load(genericClass, new File(pathToOscalFile.toUri())));
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

  public Iterable<T> findAll() {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  public Iterable<T> findAllById(Iterable<String> ids) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  public <S extends T> S save(S entity) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    throw new UnsupportedOperationException("operation not permitted.");
  }

}