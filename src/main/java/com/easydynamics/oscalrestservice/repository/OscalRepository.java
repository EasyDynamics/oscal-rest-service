package com.easydynamics.oscalrestservice.repository;

import com.easydynamics.oscalrestservice.exception.RepositoryException;
import com.easydynamics.oscalrestservice.model.OscalObject;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;
import lombok.Lombok;
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

  // protect against things like /etc/passwd with a .json at end of file check?
  // null id: IllegalArgumentException
  // DataAccessException with causes being the exception caught

  /**
   * Finds an OSCAL file and returns its contents.
   * 
   * @param id the OSCAL file name
   * @return OSCAL contents of the file, if it exists
   */
  public Optional<T> findById(String id) {
    if (id == null) {
      throw Lombok.sneakyThrow(new RepositoryException("IllegalArgumentException"));
    }

    String json;
    Path pathToOscalFile;

    try {
      pathToOscalFile = (Path.of(this.path, id));
    } catch (InvalidPathException e) {
      throw Lombok.sneakyThrow(new RepositoryException("InvalidPathException"));
    }

    try {
      json = Files.readString(pathToOscalFile, StandardCharsets.US_ASCII);
    } catch (IOException e) {
      return Optional.empty();
    } catch (OutOfMemoryError e) {
      throw Lombok.sneakyThrow(new RepositoryException("OutOfMemoryError"));
    } catch (SecurityException e) {
      throw Lombok.sneakyThrow(new RepositoryException("SecurityException"));
    }

    T response;

    try {
      response = genericClass.getDeclaredConstructor(String.class, String.class)
        .newInstance(id, json);
    } catch (InstantiationException e) {
      throw Lombok.sneakyThrow(new RepositoryException("InstantiationException"));
    } catch (IllegalAccessException e) {
      throw Lombok.sneakyThrow(new RepositoryException("IllegalAccessException"));
    } catch (IllegalArgumentException e) {
      throw Lombok.sneakyThrow(new RepositoryException("IllegalArgumentException"));
    } catch (InvocationTargetException e) {
      throw Lombok.sneakyThrow(new RepositoryException("InvocationTargetException"));
    } catch (NoSuchMethodException e) {
      throw Lombok.sneakyThrow(new RepositoryException("NoSuchMethodException"));
    } catch (SecurityException e) {
      throw Lombok.sneakyThrow(new RepositoryException("SecurityException"));
    } catch (ExceptionInInitializerError e) {
      throw Lombok.sneakyThrow(new RepositoryException("ExceptionInInitializerError"));
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