package com.easydynamics.oscal.service.impl;

import com.easydynamics.oscal.service.BaseOscalObjectService;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

/**
 * Base OSCAL object service implementation of BaseOscalObjectService.
 *
 * @param <T> the OSCAL object type
 */
public class BaseOscalObjectServiceImpl<T> implements BaseOscalObjectService<T> {

  private final CrudRepository<T, String> repository;

  protected BaseOscalObjectServiceImpl(
      CrudRepository<T, String> repository
  ) {
    this.repository = repository;
  }

  @Override
  public <S extends T> S save(S entity) {
    return repository.save(entity);
  }

  @Override
  public <S extends T> Iterable<S> saveAll(Iterable<S> entities) {
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

}
