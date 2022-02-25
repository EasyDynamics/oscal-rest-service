package com.easydynamics.oscal.data.repository.file;

import com.easydynamics.oscal.data.repository.ResourceContentRepo;
import java.io.File;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;

/**
 * File-based implementation of ResourceContentRepo.
 */
@PropertySource("classpath:application.properties")
@Repository
public class ResourceContentRepoFileImpl
    implements ResourceContentRepo {

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private final String path;

  /**
   * Constructs an OscalRepository.
   *
   * @param path path to the directory containing resource content files
   */
  protected ResourceContentRepoFileImpl(@Value("${persistence.file.content.path}") String path) {
    this.path = path;

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

  protected Optional<Path> getValidatedPathToContentFile(String id) {
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
        if (id.equals(f.getName())) {
          return Optional.of(f.toPath());
        }
      }
      return Optional.empty();
    } catch (InvalidPathException e) {
      throw new DataRetrievalFailureException("Illegal path provided.", e);
    }
  }

  @Override
  public Optional<Resource> findById(String id) {
    if (id == null) {
      throw new IllegalArgumentException("File id not provided.");
    }

    Optional<Path> pathToContentFile = getValidatedPathToContentFile(id);
    if (pathToContentFile.isEmpty()) {
      return null;
    }

    return Optional.of(new FileSystemResource(pathToContentFile.get()));
  }

}