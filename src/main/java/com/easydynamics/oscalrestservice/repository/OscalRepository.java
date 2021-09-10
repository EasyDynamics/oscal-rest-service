package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.model.OscalObject;
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

@Repository
public class OscalRepository<T extends OscalObject> implements CrudRepository<T, String> {

  // define constructor that passes in the folder.
  private Class<T> genericClass;
  public String path;

  protected OscalRepository(){

  }

  public Class<T> ac(Class<T> classObject) {
    return classObject;
  }

  protected OscalRepository(String path, Class<T> genericClass) {
    this.path = path;
    this.genericClass = genericClass;
  }

  // DataAccessException with causes being the exception caught

  /**
   * Finds an OSCAL file and returns its contents.
   * 
   * @param id the OSCAL file name
   * @return OSCAL contents of the file, if it exists
   */
  public Optional<T> findById(String id) {
    if (id == null) {
      throw new DataRetrievalFailureException("File id not provided.");
    }

    String json;
    Path pathToOscalFile;

    try {
      pathToOscalFile = (Path.of(this.path, id, ".json"));
    } catch (InvalidPathException e) {
      throw new DataRetrievalFailureException("Illegal path provided.", e);
    }

    try {
      json = Files.readString(pathToOscalFile, StandardCharsets.US_ASCII);
    } catch (IOException e) {
      return Optional.empty();
    } catch (OutOfMemoryError e) {
      throw new DataRetrievalFailureException("Could not load Oscal object.", e);
    } catch (SecurityException e) {
      throw new DataRetrievalFailureException("Could not access file.", e);
    }

    T response;

    try {
      response = genericClass.getDeclaredConstructor(String.class, String.class)
        .newInstance(id, json);
    } catch (InstantiationException | IllegalArgumentException 
      | InvocationTargetException | ExceptionInInitializerError e) {
      throw new DataRetrievalFailureException("Error creating Oscal object.", e);
    } catch (IllegalAccessException | SecurityException e) {
      throw new DataRetrievalFailureException("Access denied when creating Oscal object.", e);
    } catch (NoSuchMethodException e) {
      throw new DataRetrievalFailureException("Oscal constructor not defined.", e);
    }

    return Optional.of(response);
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