package com.easydynamics.oscal.data.repository.filepassimpl;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Handles persistence to files representing an OSCAL object, which can be a
 * Catalog, Component, Profile, or System Security Plan. Through an implementation of
 * CrudRepository, this class can count, create, delete, edit, find, and save those files.
 */
@Repository
public class BaseOscalRepoFilePassImpl<T extends Object>
    implements CrudRepository<T, String> {

  private Class<T> genericClass;
  private String path;

  protected BaseOscalRepoFilePassImpl() {

  }

  /**
   * Constructs an OscalRepository.
   *
   * @param path path to the directory containing files whose content represents an object of type
   *             T extends OscalObject.
   * @param genericClass runtime class of the generic class T, which extends OscalObject
   */
  protected BaseOscalRepoFilePassImpl(String path, Class<T> genericClass) {
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

    String json;
    Path pathToOscalFile;

    try {
      pathToOscalFile = (Path.of(this.path, id + ".json"));
      if (!pathToOscalFile.toFile().exists() || pathToOscalFile.toFile().isDirectory()) {
        return Optional.empty();
      }
    } catch (InvalidPathException e) {
      throw new DataRetrievalFailureException("Illegal path provided.", e);
    }

    try {
      json = Files.readString(pathToOscalFile, StandardCharsets.UTF_8);
    } catch (IOException e) {
      throw new DataRetrievalFailureException("Failure in loading Oscal object.", e);
    } catch (OutOfMemoryError e) {
      throw new DataRetrievalFailureException("Could not load Oscal object.", e);
    } catch (SecurityException e) {
      throw new DataRetrievalFailureException("Could not access file.", e);
    }

    try {
      return Optional.of(genericClass.getDeclaredConstructor(String.class, String.class)
        .newInstance(id, json));
    } catch (InstantiationException | IllegalArgumentException
      | InvocationTargetException | ExceptionInInitializerError e) {
      throw new DataRetrievalFailureException("Error creating Oscal object.", e);
    } catch (IllegalAccessException | SecurityException e) {
      throw new DataRetrievalFailureException("Access denied when creating Oscal object.", e);
    } catch (NoSuchMethodException e) {
      throw new DataRetrievalFailureException("Oscal constructor not defined.", e);
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