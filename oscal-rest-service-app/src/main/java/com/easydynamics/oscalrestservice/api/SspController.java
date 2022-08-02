package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.OscalSspService;
import com.easydynamics.oscal.service.impl.OscalDeepCopyUtils;
import gov.nist.secauto.oscal.lib.model.ByComponent;
import gov.nist.secauto.oscal.lib.model.ControlImplementation;
import gov.nist.secauto.oscal.lib.model.ImplementedRequirement;
import gov.nist.secauto.oscal.lib.model.Statement;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

/**
 * Ssp Controller for OSCAL REST Service.
 * This class handles all requests to the /system-security-plans endpoint.
 */
@RequestMapping(path = "/oscal/v1")
@RestController
public class SspController extends BaseOscalController<SystemSecurityPlan> {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  private final OscalObjectMarshaller<ImplementedRequirement> oscalSspImplReqMarshaller;
  private final OscalObjectMarshaller<Statement> oscalSspStatementMarshaller;
  private final OscalObjectMarshaller<ByComponent> oscalSspByComponentMarshaller;

  /**
   * This is the constructor for the SspController.
   * 
   * @param sspService A service for the Ssp.
   * @param marshaller SSP marshaller serializing JSON data.
   * @param oscalSspImplReqtMarshaller Implemented Request marshaller serializing JSON data.
   * @param oscalSspStatementMarshaller Statement marshaller serializing JSON data.
   * @param oscalSspByComponentMarshaller ByComponent marshaller serializing JSON data.
   */
  @Autowired(required = true)
  public SspController(
      OscalSspService sspService,
      OscalObjectMarshaller<SystemSecurityPlan> marshaller,
      OscalObjectMarshaller<ImplementedRequirement> oscalSspImplReqtMarshaller,
      OscalObjectMarshaller<Statement> oscalSspStatementMarshaller,
      OscalObjectMarshaller<ByComponent> oscalSspByComponentMarshaller
  ) {
    super(sspService, marshaller);
    this.oscalSspImplReqMarshaller = oscalSspImplReqtMarshaller;
    this.oscalSspStatementMarshaller = oscalSspStatementMarshaller;
    this.oscalSspByComponentMarshaller = oscalSspByComponentMarshaller;
  }

  @GetMapping("/system-security-plans")
  public ResponseEntity<StreamingResponseBody> findAll() {
    return super.findAll();
  }

  /**
   * Defines a GET request for ssp by ID.
   *
   * @param id the ssp uuid
   * @return the oscal-content ssp-example hosted on github
   */
  @GetMapping("/system-security-plans/{id}")
  public ResponseEntity<StreamingResponseBody> findById(@Parameter @PathVariable String id) {
    return super.findById(id);
  }

  /**
   * Defines a PATCH request for updating SSPs.
   *
   * @param id   the SSP uuid
   * @param json the SSP contents
   */
  @PatchMapping("/system-security-plans/{id}")
  public ResponseEntity<StreamingResponseBody> patch(
      @Parameter @PathVariable String id,
      @RequestBody String json) {
    return super.patch(id, json);
  }

  /**
   * Defines a PUT request for updating SSPs.
   *
   * @param id   the SSP uuid
   * @param json the SSP contents
   */
  @PutMapping(value = "/system-security-plans/{id}",
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> put(
      @Parameter @PathVariable String id,
      @RequestBody String json) {
    return super.put(id, json);
  }

  /**
   * Similar to unmarshallAndValidateId, checks that the given id
   * matches the UUID in the given json.
   *
   * @param id   the request path id
   * @param json the request body json
   * @return the unmarshalled object
   * @throws OscalObjectConflictException when the path ID does not match the body
   *                                      ID
   */
  protected ImplementedRequirement unmarshallImplReqAndValidateId(String id, String json) {
    ImplementedRequirement incomingOscalObject = oscalSspImplReqMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    UUID incomingUuid = incomingOscalObject.getUuid();
    if (incomingUuid != null && !id.equals(incomingUuid.toString())) {
      throw new OscalObjectConflictException(incomingUuid.toString(), id);
    }

    return incomingOscalObject;
  }

  /**
   * Finds an implemented requirement from an SSP based on the given uuid.
   *
   * @param implementedRequirementId the Implemented Requirement uuid
   * @param statementId              the Statement uuid
   */
  protected Optional<ImplementedRequirement> getSspImplementedRequirement(
      SystemSecurityPlan existingSsp,
      String implementedRequirementId) {
    if (existingSsp == null) {
      throw new IllegalArgumentException("Ssp not provided.");
    }
    if (implementedRequirementId == null) {
      throw new IllegalArgumentException("Implemented Requirement id not provided.");
    }

    return existingSsp.getControlImplementation().getImplementedRequirements().stream()
        .filter(implReq -> implementedRequirementId.equals(implReq.getUuid().toString()))
        .findAny();
  }

  /**
   * Helper function to add a new Implemented Requirement to the list
   * of Implemented Requirements in a given SSP.
   *
   * @param existingSsp     the SSP object to update
   * @param incomingImplReq the new Implemented Requirement to add
   *
   */
  private void addImplReqToList(
      SystemSecurityPlan existingSsp,
      ImplementedRequirement incomingImplReq) {
    ControlImplementation controlImplementation = existingSsp.getControlImplementation();
    if (controlImplementation == null) {
      controlImplementation = new ControlImplementation();
      existingSsp.setControlImplementation(controlImplementation);
    }
    List<ImplementedRequirement> implReqs = controlImplementation.getImplementedRequirements();
    if (implReqs == null) {
      implReqs = new ArrayList<>();
      controlImplementation.setImplementedRequirements(implReqs);
    }
    implReqs.add(incomingImplReq);
  }

  /**
   * Does the work of finding an existing SSP and updating it with the
   * given Implemented Requirement.
   *
   * @param id                       the SSP UUID
   * @param implementedRequirementId the impl req UUID
   * @param json                     the Implemented Requirement JSON
   * @param isCreateOnly             requires no impl req with the same UUID exist
   *                                 when true
   * @return the response
   */
  private ResponseEntity<StreamingResponseBody> updateImplementedRequirement(
      String id,
      String implementedRequirementId,
      String json) {
    SystemSecurityPlan existingSsp = oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    ImplementedRequirement incomingImplReq =
        unmarshallImplReqAndValidateId(implementedRequirementId, json);

    // Find existing ImplementedRequirement if exists and merge or add
    Optional<ImplementedRequirement> existingImplReq = Optional.empty();
    if (existingSsp.getControlImplementation() != null
        && existingSsp.getControlImplementation().getImplementedRequirements() != null) {
      existingImplReq = getSspImplementedRequirement(existingSsp, implementedRequirementId);
    }
    if (!existingImplReq.isPresent()) {
      addImplReqToList(existingSsp, incomingImplReq);
    } else {
      try {
        OscalDeepCopyUtils.deepCopyProperties(existingImplReq.get(), incomingImplReq);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new InvalidDataAccessResourceUsageException(
            "Could not deep copy object", e);
      }
    }

    logger.debug("SSP ImplementedRequirement updated, saving via service");
    oscalObjectService.save(existingSsp);
    return makeObjectResponse(incomingImplReq, oscalSspImplReqMarshaller);
  }

  /**
   * Does the work of finding an existing SSP and adding the
   * new Implemented Requirement.
   *
   * @param id   the SSP UUID
   * @param json the Implemented Requirement JSON
   * @return the response
   */
  private ResponseEntity<StreamingResponseBody> addImplementedRequirement(
      String id,
      String json) {
    SystemSecurityPlan existingSsp = oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    ImplementedRequirement incomingImplReq = oscalSspImplReqMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    // Throw an exception if the Implemented Requirement already exists
    if (existingSsp.getControlImplementation() != null
        && existingSsp.getControlImplementation().getImplementedRequirements() != null
        && existingSsp.getControlImplementation().getImplementedRequirements().stream()
            .anyMatch(implReq -> incomingImplReq.getUuid().equals(implReq.getUuid()))) {
      throw new OscalObjectConflictException("Implemented Requirement already exists");
    }

    addImplReqToList(existingSsp, incomingImplReq);

    logger.debug("SSP ImplementedRequirement updated, saving via service");

    return makeObjectResponse(oscalObjectService.save(existingSsp));
  }

  /**
   * Defines a POST request for adding SSPs control implementation
   * implemented requirements.
   *
   * @param id   the SSP uuid
   * @param json the SSP contents
   */
  @PostMapping(value = "/system-security-plans/{id}/control-implementation/"
      + "implemented-requirements", consumes = {
          MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> updateImplementedRequirementPost(
      @Parameter @PathVariable String id,
      @RequestBody String json) {
    return addImplementedRequirement(id, json);
  }

  /**
   * Defines a PUT request for updating SSPs control implementation
   * implemented requirements.
   *
   * @param id                       the SSP uuid
   * @param implementedRequirementId the Implemented Requirement uuid
   * @param json                     the SSP contents
   */
  @PutMapping(value = "/system-security-plans/{id}/control-implementation/"
      + "implemented-requirements/{implementedRequirementId}", consumes = {
          MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> updateImplementedRequirementPut(
      @Parameter @PathVariable String id,
      @Parameter @PathVariable String implementedRequirementId,
      @RequestBody String json) {
    return updateImplementedRequirement(id, implementedRequirementId, json);
  }

  /**
   * Helper function to add a new Statement to the list
   * of Statement in a given Implemented Requirements of an Ssp.
   *
   * @param existingImplReq   the Implemented Requirement to update
   * @param incomingStatement the new statement to add
   *
   */
  private void addStatementToList(
      ImplementedRequirement existingImplReq,
      Statement incomingStatement) {
    List<Statement> stmts = existingImplReq.getStatements();
    if (stmts == null) {
      stmts = new ArrayList<>();
      existingImplReq.setStatements(stmts);
    }

    stmts.add(incomingStatement);
  }

  /**
   * Similar to unmarshallAndValidateId, checks that the given id
   * matches the UUID in the given json for a statement.
   *
   * @param id   the request path id
   * @param json the request body json
   * @return the unmarshalled object
   * @throws OscalObjectConflictException when the path ID does not match the body
   *                                      ID
   */
  protected Statement unmarshallStatementAndValidateId(String id, String json) {
    Statement incomingOscalObject = oscalSspStatementMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    UUID incomingUuid = incomingOscalObject.getUuid();
    if (incomingUuid != null && !id.equals(incomingUuid.toString())) {
      throw new OscalObjectConflictException(incomingUuid.toString(), id);
    }

    return incomingOscalObject;
  }

  /**
   * Finds a statement from an implemented requirement based on the given uuid.
   *
   * @param existingImplReq the Implemented Requirement uuid
   * @param statementId     the Statement uuid
   */
  protected Optional<Statement> getSspStatement(
      ImplementedRequirement existingImplReq,
      String statementId) {
    if (existingImplReq == null) {
      throw new IllegalArgumentException("Implemented Requirement not provided.");
    }
    if (statementId == null) {
      throw new IllegalArgumentException("Statement id not provided.");
    }

    return existingImplReq.getStatements().stream()
        .filter(stmt -> statementId.equals(stmt.getUuid().toString()))
        .findAny();
  }

  /**
   * Does the work of finding an existing SSP and updating it with the
   * given Statement.
   *
   * @param id                       the SSP UUID
   * @param implementedRequirementId the Implemented Requirement UUID
   * @param statementId              the Statement UUID
   * @param json                     the Statement JSON
   * @param isCreateOnly             requires no Statement with the same UUID
   *                                 exist
   *                                 when true
   * @return the response
   */
  private ResponseEntity<StreamingResponseBody> updateStatement(
      String id,
      String statementId,
      String implementedRequirementId,
      String json) {
    SystemSecurityPlan existingSsp = oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    Statement incomingStmt = unmarshallStatementAndValidateId(statementId, json);

    Optional<ImplementedRequirement> existingImplReq = getSspImplementedRequirement(existingSsp,
        implementedRequirementId);

    // Find existing Statement if exists and merge or add
    Optional<Statement> existingStmt = Optional.empty();
    if (existingSsp.getControlImplementation() != null
        && existingSsp.getControlImplementation().getImplementedRequirements() != null
        && existingImplReq != null
        && existingSsp.getControlImplementation().getImplementedRequirements().stream()
            .anyMatch(implReq -> existingImplReq.get().getUuid().equals(implReq.getUuid()))) {
      existingStmt = getSspStatement(existingImplReq.get(), statementId);
    }
    if (!existingStmt.isPresent()) {
      addStatementToList(existingImplReq.get(), incomingStmt);
    } else {
      try {
        OscalDeepCopyUtils.deepCopyProperties(existingStmt.get(), incomingStmt);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new InvalidDataAccessResourceUsageException(
            "Could not deep copy object", e);
      }
    }

    logger.debug("SSP Statement updated, saving via service");
    oscalObjectService.save(existingSsp);
    return makeObjectResponse(incomingStmt, oscalSspStatementMarshaller);
  }

  /**
   * Similar to unmarshallAndValidateId, checks that the given statement id
   * matches the UUID in the given json.
   *
   * @param id   the request path id
   * @param json the request body json
   * @return the unmarshalled object
   * @throws OscalObjectConflictException when the path ID does not match the body
   *                                      ID
   */
  protected Statement unmarshallStatement(String id, String json) {
    Statement incomingOscalObject = oscalSspStatementMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    UUID incomingUuid = incomingOscalObject.getUuid();
    if (incomingUuid != null && !id.equals(incomingUuid.toString())) {
      throw new OscalObjectConflictException(incomingUuid.toString(), id);
    }

    return incomingOscalObject;
  }  

  /**
   * Does the work of finding an existing SSP and adding
   * a statements' by-component Id.
   *
   * @param id                       the SSP UUID
   * @param implementedRequirementId the Implemented Requirement uuid
   * @param json                     the SSP contents
   */
  private ResponseEntity<StreamingResponseBody> addStatement(
      String id,
      String implementedRequirementId,
      String json) {
    SystemSecurityPlan existingSsp = oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    Optional<ImplementedRequirement> existingImplReq = getSspImplementedRequirement(existingSsp,
        implementedRequirementId);

    Statement incomingStmt = oscalSspStatementMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    // Throw an exception if statement already exists
    if (existingSsp.getControlImplementation().getImplementedRequirements() != null
        && existingSsp.getControlImplementation().getImplementedRequirements() != null
        && existingSsp.getControlImplementation().getImplementedRequirements().stream()
            .anyMatch(implReq -> existingImplReq.get().getUuid().equals(implReq.getUuid()))
        && existingImplReq.get().getStatements().stream().anyMatch(stmt ->
            incomingStmt.getUuid().equals(stmt.getUuid()))) {
      throw new OscalObjectConflictException("Implemented Statement already exists");
    }

    addStatementToList(existingImplReq.get(), incomingStmt);

    logger.debug("SSP Statement updated, saving via service");

    return makeObjectResponse(oscalObjectService.save(existingSsp));
  }

  /**
   * Defines a POST request for adding a statement Id.
   *
   * @param id                       the SSP uuid
   * @param implementedRequirementId the Implemented Requirement uuid
   * @param json                     the SSP contents
   */
  @PostMapping(value = "/system-security-plans/{id}/control-implementation/"
      + "implemented-requirements/{implementedRequirementId}/statements",
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> updateStatementPost(
      @Parameter @PathVariable String id,
      @Parameter @PathVariable String implementedRequirementId,
      @RequestBody String json) {
    return addStatement(id, implementedRequirementId, json);
  }

  /**
   * Defines a PUT request for updating a statement Id.
   *
   * @param id                       the SSP uuid
   * @param implementedRequirementId the Implemented Requirement uuid
   * @param statementId              the Statement uuid
   * @param json                     the SSP contents
   */
  @PutMapping(value = "/system-security-plans/{id}/control-implementation/"
      + "implemented-requirements/{implementedRequirementId}/statements/{statementId}",
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> updateStatementIdPut(
      @Parameter @PathVariable String id,
      @Parameter @PathVariable String implementedRequirementId,
      @Parameter @PathVariable String statementId,
      @RequestBody String json) {
    return updateStatement(id, implementedRequirementId, statementId, json);
  }

  /**
   * Helper function to add a new ByComponent to the list
   * of ByComponents in a given Statement of an Ssp.
   *
   * @param existingStatement   the statement to update
   * @param incomingByComponent the new by-component to add
   *
   */
  private void addComponentToList(
      Statement existingStatement,
      ByComponent incomingByComponent) {
    List<ByComponent> byComps = existingStatement.getByComponents();
    if (byComps == null) {
      byComps = new ArrayList<>();
      existingStatement.setByComponents(byComps);
    }

    byComps.add(incomingByComponent);
  }

  /**
   * Similar to unmarshallAndValidateId, checks that the given id
   * matches the UUID in the given json for a by-component.
   *
   * @param id   the request path id
   * @param json the request body json
   * @return the unmarshalled object
   * @throws OscalObjectConflictException when the path ID does not match the body
   *                                      ID
   */
  protected ByComponent unmarshallComponentAndValidateId(String id, String json) {
    ByComponent incomingOscalObject = oscalSspByComponentMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    UUID incomingUuid = incomingOscalObject.getUuid();
    if (incomingUuid != null && !id.equals(incomingUuid.toString())) {
      throw new OscalObjectConflictException(incomingUuid.toString(), id);
    }

    return incomingOscalObject;
  }

  /**
   * Finds a by-component from an implemented requirement based on the given uuid.
   *
   * @param existingImplReq the Implemented Requirement uuid
   * @param componentId     the Statement uuid
   */
  protected Optional<ByComponent> getSspByComponent(
      Statement existingStmt,
      String componentId) {
    if (existingStmt == null) {
      throw new IllegalArgumentException("Statement not provided.");
    }
    if (componentId == null) {
      throw new IllegalArgumentException("Component id not provided.");
    }

    return existingStmt.getByComponents().stream()
        .filter(byComp -> componentId.equals(byComp.getUuid().toString()))
        .findAny();
  }


  /**
   * Does the work of finding an existing SSP and updating it with the
   * given by-component.
   *
   * @param id                       the SSP UUID
   * @param implementedRequirementId the Implemented Requirement UUID
   * @param statementId              the Statement UUID
   * @param componentId              the Component UUID
   * @param json                     the Component JSON
   * @param isCreateOnly             requires no Component with the same UUID
   *                                 exist
   *                                 when true
   * @return the response
   */
  private ResponseEntity<StreamingResponseBody> updateComponent(
      String id,
      String statementId,
      String componentId,
      String implementedRequirementId,
      String json) {
    SystemSecurityPlan existingSsp = oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    ByComponent incomingByComp = unmarshallComponentAndValidateId(componentId, json);

    Optional<ImplementedRequirement> existingImplReq = getSspImplementedRequirement(existingSsp,
        implementedRequirementId);
    Optional<Statement> existingStmt = getSspStatement(existingImplReq.get(), statementId);

    // Find existing ByComponent if exists and merge or add
    Optional<ByComponent> existingByComp = Optional.empty();
    if (existingSsp.getControlImplementation() != null
        && existingSsp.getControlImplementation().getImplementedRequirements() != null
        && existingImplReq != null
        && existingSsp.getControlImplementation().getImplementedRequirements().stream()
            .anyMatch(implReq -> existingImplReq.get().getUuid().equals(implReq.getUuid()))
        && existingImplReq.get().getStatements().stream()
            .anyMatch(stmt -> existingStmt.get().getUuid().equals(stmt.getUuid()))) {
      existingByComp = getSspByComponent(existingStmt.get(), componentId);
    }
    if (!existingByComp.isPresent()) {
      addComponentToList(existingStmt.get(), incomingByComp);
    } else {
      try {
        OscalDeepCopyUtils.deepCopyProperties(existingByComp.get(), incomingByComp);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new InvalidDataAccessResourceUsageException(
            "Could not deep copy object", e);
      }
    }

    logger.debug("SSP Component updated, saving via service");
    oscalObjectService.save(existingSsp);
    return makeObjectResponse(incomingByComp, oscalSspByComponentMarshaller);
  }

  /**
   * Similar to unmarshallAndValidateId, checks that the given by-component id
   * matches the UUID in the given json.
   *
   * @param id   the request path id
   * @param json the request body json
   * @return the unmarshalled object
   * @throws OscalObjectConflictException when the path ID does not match the body
   *                                      ID
   */
  protected ByComponent oscalSspByComponentMarshaller(String id, String json) {
    ByComponent incomingOscalObject = oscalSspByComponentMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    UUID incomingUuid = incomingOscalObject.getUuid();
    if (incomingUuid != null && !id.equals(incomingUuid.toString())) {
      throw new OscalObjectConflictException(incomingUuid.toString(), id);
    }

    return incomingOscalObject;
  }

  /**
   * Does the work of finding an existing SSP and adding
   * a statements' by-component Id.
   *
   * @param id                       the SSP UUID
   * @param implementedRequirementId the Implemented Requirement uuid
   * @param statementId              the Statement uuid
   * @param json                     the SSP contents
   */
  private ResponseEntity<StreamingResponseBody> addComponent(
      String id,
      String implementedRequirementId,
      String statementId,
      String json) {
    SystemSecurityPlan existingSsp = oscalObjectService.findById(id)
        .orElseThrow(() -> new OscalObjectNotFoundException(id));

    Optional<ImplementedRequirement> existingImplReq = getSspImplementedRequirement(existingSsp,
        implementedRequirementId);
    Optional<Statement> existingStmt = getSspStatement(existingImplReq.get(), statementId);

    ByComponent incomingByComp = oscalSspByComponentMarshaller.toObject(
        new ByteArrayInputStream(json.getBytes()));

    // Throw an exception if statement already exists
    if (existingSsp.getControlImplementation().getImplementedRequirements() != null
        && existingSsp.getControlImplementation().getImplementedRequirements() != null
        && existingSsp.getControlImplementation().getImplementedRequirements().stream()
            .anyMatch(implReq -> existingImplReq.get().getUuid().equals(implReq.getUuid()))
        && existingImplReq.get().getStatements().stream().anyMatch(stmt ->
            existingStmt.get().getUuid().equals(stmt.getUuid()))
        && existingStmt.get().getByComponents().stream()
            .anyMatch(byComp -> incomingByComp.getUuid().equals(byComp.getUuid()))) {
      throw new OscalObjectConflictException("Implemented Component already exists");
    }

    addComponentToList(existingStmt.get(), incomingByComp);

    logger.debug("SSP Component updated, saving via service");

    return makeObjectResponse(oscalObjectService.save(existingSsp));
  }

  /**
   * Defines a POST request for adding a statements' by-component Id.
   *
   * @param id                       the SSP uuid
   * @param implementedRequirementId the Implemented Requirement uuid
   * @param statementId              the Statement uuid
   * @param json                     the SSP contents
   */
  @PostMapping(value = "/system-security-plans/{id}/control-implementation/"
      + "implemented-requirements/{implementedRequirementId}/statements/{statementId}/"
      + "by-components", consumes = {
          MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> updateComponentIdPost(
      @Parameter @PathVariable String id,
      @Parameter @PathVariable String implementedRequirementId,
      @Parameter @PathVariable String statementId,
      @RequestBody String json) {
    return addComponent(id, implementedRequirementId, statementId, json);
  }

  /**
   * Defines a PUT request for updating a statements' by-component Id.
   *
   * @param id                       the SSP uuid
   * @param implementedRequirementId the Implemented Requirement uuid
   * @param statementId              the Statement uuid
   * @param componentId              the By-component uuid
   * @param json                     the SSP contents
   */
  @PutMapping(value = "/system-security-plans/{id}/control-implementation/"
      + "implemented-requirements/{implementedRequirementId}/statements/{statementId}/"
      + "by-components/{componentId}", consumes = {
          MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> updateComponentIdPut(
      @Parameter @PathVariable String id,
      @Parameter @PathVariable String implementedRequirementId,
      @Parameter @PathVariable String statementId,
      @Parameter @PathVariable String componentId,
      @RequestBody String json) {
    return updateComponent(id, implementedRequirementId, statementId, componentId, json);
  }
}
