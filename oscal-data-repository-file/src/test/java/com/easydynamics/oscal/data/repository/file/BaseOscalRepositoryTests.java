package com.easydynamics.oscal.data.repository.file;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.StreamSupport;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.easydynamics.oscal.data.example.ExampleContent;

import gov.nist.secauto.metaschema.model.common.datatype.markup.MarkupLine;
import gov.nist.secauto.oscal.lib.model.Metadata;

/**
 * OscalRepositoryTests is an abstract class that provides methods for common tests among the
 * classes running tests of the different OscalRepository types.
 */
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RepositoryConfig.class)
public abstract class BaseOscalRepositoryTests<T extends Object> {

  @Autowired
  protected CrudRepository<T, String> repository;
  protected ExampleContent exampleContent;

  private static final String EXPECTED_TITLE = "Some New Title";

  /**
   * Tests if a null id will cause an OSCAL repository to throw an IllegalArgumentException when
   * trying to find a file.
   */
  @Test
  public void nullFindById() {
    assertThrows(IllegalArgumentException.class, () -> repository.findById(null));
  }

  /**
   * Tests if an OscalRepository returns an empty optional object when trying to find a file
   * using an invalid id.
   */
  @Test
  public void badIdOptionalEmpty() {
    assertEquals(Optional.empty(), repository.findById("bad-id"));
  }

  protected String getObjectUuid(T oscalObject) throws Exception {
    Method getUuid = oscalObject.getClass().getMethod("getUuid");
    return getUuid.invoke(oscalObject).toString();
  }

  /**
   * Tests if an OscalRepository returns content when trying to find a file using a valid id.
   * @throws Exception
   */
  @Test
  public void getGoodId() throws Exception {
    T oscalObject = repository.findById(exampleContent.uuid).get();
    assertNotNull(oscalObject);
    String uuid = getObjectUuid(oscalObject);

    assertEquals(exampleContent.uuid, uuid);
  }

  /**
   * Tests if an OscalRepository returns content when trying to find a file using a valid id.
   * @throws Exception
   */
  @Test
  public void getGoodIdByFileName() throws Exception {
    T oscalObject = repository.findById(exampleContent.fileName).get();
    assertNotNull(oscalObject);
    String uuid = getObjectUuid(oscalObject);

    assertEquals(exampleContent.uuid, uuid);
  }

  /**
   * Tests if an OscalRepository returns content when trying to save a file using a valid id.
   * @throws Exception
   */
  @Test
  public void testSave() throws Exception {
    T oscalObject = repository.findById(exampleContent.uuid).get();
    assertNotNull(oscalObject);
    Method getMetadata = oscalObject.getClass().getMethod("getMetadata");
    Metadata metadata = (Metadata) getMetadata.invoke(oscalObject);

    MarkupLine newTitle = MarkupLine.fromMarkdown(EXPECTED_TITLE);
    metadata.setTitle(newTitle);

    repository.save(oscalObject);

    // Reload the object and confirm title
    T savedOscalObject = repository.findById(exampleContent.uuid).get();
    assertNotNull(savedOscalObject);
    Metadata savedMetadata = (Metadata) getMetadata.invoke(savedOscalObject);

    assertEquals(EXPECTED_TITLE, savedMetadata.getTitle().toMarkdown());
  }

  /**
   * Tests that an OscalRepository returns a list for valid findAll executions.
   * @throws Exception
   */
  @Test
  public void testFindAll() throws Exception {
    Iterable<T> oscalObjects = repository.findAll();
    assertNotNull(oscalObjects);
    T foundObject = StreamSupport.stream(oscalObjects.spliterator(), false)
      .filter(oscalObject -> {
        try {
          return exampleContent.uuid.equals(getObjectUuid(oscalObject));
        } catch (Exception e) {
          return false;
        }
      })
      .findAny()
      .orElse(null);

    assertNotNull(foundObject);
  }

  @Test
  public void testExistsByIdForValidId() throws Exception {
    assertTrue(repository.existsById(exampleContent.uuid));
  }

  @Test
  public void testExistsByIdForInvalidId() throws Exception {
    assertFalse(repository.existsById("NOT-A-UUID"));
  }
}
