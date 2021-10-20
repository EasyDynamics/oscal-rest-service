package com.easydynamics.oscalrestservice.repository;

import gov.nist.secauto.metaschema.binding.io.BindingException;
import gov.nist.secauto.oscal.java.OscalLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * OscalRepository handles persistence to files representing an OSCAL object, which can be a
 * Catalog, Component, Profile, or System Security Plan. Through an implementation of 
 * CrudRepository, OscalRepository can count, create, delete, edit, find, and save those files.
 */
@Repository
public class OscalRepository<T> implements CrudRepository<T, String> {

  private Class<T> genericClass;
  private String path;
  private OscalLoader oscalLoader;

  protected OscalRepository(){

  }

  /**
   * Constructs an OscalRepository.
   * 
   * @param path path to the directory containing files whose content represents an object of type 
   *             T extends OscalObject.
   * @param genericClass runtime class of the generic class T, which extends OscalObject
   */
  protected OscalRepository(String path, Class<T> genericClass) {
    this.path = path;
    this.genericClass = genericClass;
    this.oscalLoader = new OscalLoader();
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
      if (!pathToOscalFile.toFile().exists() || pathToOscalFile.toFile().isDirectory()) {
        return Optional.empty();
      }
    } catch (InvalidPathException e) {
      throw new DataRetrievalFailureException("Illegal path provided.", e);
    }

    try {
      T oscalPojo = oscalLoader.load(genericClass, new File(pathToOscalFile.toUri()));
      return Optional.of(oscalPojo);
    } catch (FileNotFoundException e) {
      throw new DataRetrievalFailureException("Could not access file.", e);
    } catch (BindingException e) {
      throw new DataRetrievalFailureException("Error creating Oscal object.", e);
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