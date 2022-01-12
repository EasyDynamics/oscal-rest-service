package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.service.BaseOscalObjectService;
import gov.nist.secauto.oscal.lib.model.Metadata;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.StreamSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.data.repository.CrudRepository;

/**
 * Base OSCAL object service implementation of BaseOscalObjectService.
 *
 * @param <T> the OSCAL object type
 */
public abstract class BaseOscalObjectServiceImpl<T> implements BaseOscalObjectService<T> {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final CrudRepository<T, String> repository;

  protected BaseOscalObjectServiceImpl(
      CrudRepository<T, String> repository
  ) {
    this.repository = repository;
  }

  /**
   * Updates the given entity's last modified metadata field to now.
   *
   * @param <S> the entity type
   * @param entity the entity to update
   */
  protected <S extends T> void updateLastModified(S entity) {
    if (entity == null) {
      throw new IllegalArgumentException("entity a must not be null");
    }

    try {
      Method getMetadata = entity.getClass().getMethod("getMetadata");
      Metadata metadata = (Metadata) getMetadata.invoke(entity);

      if (metadata == null) {
        metadata = new Metadata();
      }

      metadata.setLastModified(ZonedDateTime.now().withFixedOffsetZone());

      Method setMetadata = entity.getClass().getMethod("setMetadata", Metadata.class);
      setMetadata.invoke(entity, metadata);
      logger.debug("Updated last modified metadata for objext type: {}",
          entity.getClass().getSimpleName());
    } catch (IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      throw new InvalidDataAccessResourceUsageException(
          "could not updated last modified metadata", e);
    }
  }

  @Override
  public UUID getUuid(T object) {
    try {
      Method getUuid = object.getClass().getMethod("getUuid");
      return (UUID) getUuid.invoke(object);
    } catch (IllegalAccessException | IllegalArgumentException
        | InvocationTargetException | NoSuchMethodException
        | SecurityException e) {
      throw new InvalidDataAccessResourceUsageException(
          "could not get UUID", e);
    }
  }

  @Override
  public <S extends T> S save(S entity) {
    updateLastModified(entity);
    return repository.save(entity);
  }

  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
    StreamSupport.stream(entities.spliterator(), false)
        .forEach(entity -> updateLastModified(entity));
    return repository.saveAll(entities);
  }

  @Override
  public Optional<T> findById(String id) {
    return repository.findById(id);
  }

  @Override
  public boolean existsById(String id) {
    return repository.existsById(id);
  }

  @Override
  public Iterable<T> findAll() {
    return repository.findAll();
  }

  @Override
  public Iterable<T> findAllById(Iterable<String> ids) {
    return repository.findAllById(ids);
  }

  @Override
  public long count() {
    return repository.count();
  }

  @Override
  public void deleteById(String id) {
    repository.deleteById(id);
  }

  @Override
  public void delete(T entity) {
    repository.delete(entity);
  }

  @Override
  public void deleteAll(Iterable<? extends T> entities) {
    repository.deleteAll(entities);
  }

  @Override
  public void deleteAll() {
    repository.deleteAll();
  }

  @Override
  public void deleteAllById(Iterable<? extends String> ids) {
    repository.deleteAllById(ids);
  }

  @Override
  public T merge(T source, T target) {
    try {
      OscalDeepCopyUtils.deepCopyProperties(target, source);
      return target;
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new InvalidDataAccessResourceUsageException(
          "could not deep copy object", e);
    }
  }

}
