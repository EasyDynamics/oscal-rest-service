package com.easydynamics.oscalrestservice.api;

import com.easydynamics.oscal.data.marshalling.OscalObjectMarshaller;
import com.easydynamics.oscal.service.OscalSspService;
import com.easydynamics.oscal.service.impl.OscalDeepCopyUtils;
import gov.nist.secauto.oscal.lib.model.ControlImplementation;
import gov.nist.secauto.oscal.lib.model.ImplementedRequirement;
import gov.nist.secauto.oscal.lib.model.SystemSecurityPlan;
import java.io.ByteArrayInputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
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

  @Autowired(required = true)
  public SspController(
      OscalSspService sspService,
      OscalObjectMarshaller<SystemSecurityPlan> marshaller,
      OscalObjectMarshaller<ImplementedRequirement> oscalSspImplReqtMarshaller
  ) {
    super(sspService, marshaller);
    this.oscalSspImplReqMarshaller = oscalSspImplReqtMarshaller;
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
  public ResponseEntity<StreamingResponseBody> findById(@PathVariable String id) {
    return super.findById(id);
  }

  /**
   * Defines a PATCH request for updating SSPs.
   *
   * @param id the SSP uuid
   * @param json the SSP contents
   */
  @PatchMapping("/system-security-plans/{id}")
  public ResponseEntity<StreamingResponseBody> patch(
      @PathVariable String id,
      @RequestBody String json) {
    return super.patch(id, json);
  }

  /**
   * Defines a PUT request for updating SSPs.
   *
   * @param id the SSP uuid
   * @param json the SSP contents
   */
  @PutMapping(value = "/system-security-plans/{id}",
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> put(
      @PathVariable String id,
      @RequestBody String json) {
    return super.put(id, json);
  }

  /**
   * Similar to unmarshallAndValidateId, checks that the given id
   * matches the UUID in the given json.
   *
   * @param id the request path id
   * @param json the request body json
   * @return the unmarshalled object
   * @throws OscalObjectConflictException when the path ID does not match the body ID
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
  * Helper function to add a new Implemented Requirement to the list
  * of Implemented Requirements in a given SSP.
  *
  * @param existingSsp the SSP object to update
  * @param incomingImplReq the new Implemented Requirement to add
  *
  */
  private void addImplReqToList(
      SystemSecurityPlan existingSsp,
      ImplementedRequirement incomingImplReq
  ) {
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
   * @param id the SSP UUID
   * @param implementedRequirementId the impl req UUID
   * @param json the Implemented Requirement JSON
   * @param isCreateOnly requires no impl req with the same UUID exist when true
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
    ImplementedRequirement existingImplReq = null;
    if (existingSsp.getControlImplementation() != null
        && existingSsp.getControlImplementation().getImplementedRequirements() != null) {
      existingImplReq = existingSsp.getControlImplementation().getImplementedRequirements().stream()
          .filter(implReq -> incomingImplReq.getUuid().equals(implReq.getUuid()))
          .findAny()
          .orElse(null);
    }
    if (existingImplReq == null) {
      addImplReqToList(existingSsp, incomingImplReq);
    } else {
      try {
        OscalDeepCopyUtils.deepCopyProperties(existingImplReq, incomingImplReq);
      } catch (IllegalAccessException | InvocationTargetException e) {
        throw new InvalidDataAccessResourceUsageException(
            "could not deep copy object", e);
      }
    }

    logger.debug("SSP ImplementedRequiremnt updated, saving via service");
    oscalObjectService.save(existingSsp);
    return makeObjectResponse(incomingImplReq, oscalSspImplReqMarshaller);
  }

  /**
   * Does the work of finding an existing SSP and adding the
   * new Implemented Requirement.
   *
   * @param id the SSP UUID
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

    // Throw an exception if the Implemented Requirement alrady exists
    if (existingSsp.getControlImplementation() != null
        && existingSsp.getControlImplementation().getImplementedRequirements() != null
        && existingSsp.getControlImplementation().getImplementedRequirements().stream()
          .anyMatch(implReq -> incomingImplReq.getUuid().equals(implReq.getUuid()))) {
      throw new OscalObjectConflictException("Implemented Requirement already exists");
    }

    addImplReqToList(existingSsp, incomingImplReq);

    logger.debug("SSP ImplementedRequiremnt updated, saving via service");

    return makeObjectResponse(oscalObjectService.save(existingSsp));
  }

  /**
   * Defines a POST request for adding SSPs control implementation
   * implemented requirements.
   *
   * @param id the SSP uuid
   * @param json the SSP contents
   */
  @PostMapping(value = "/system-security-plans/{id}/control-implementation/"
      + "implemented-requirements",
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> updateImplementedRequirementPost(
      @PathVariable String id,
      @RequestBody String json) {
    return addImplementedRequirement(id, json);
  }

  /**
   * Defines a PUT request for updating SSPs control implementation
   * implemented requirements.
   *
   * @param id the SSP uuid
   * @param implementedRequirementId the Implemented Requirement uuid
   * @param json the SSP contents
   */
  @PutMapping(value = "/system-security-plans/{id}/control-implementation/"
      + "implemented-requirements/{implementedRequirementId}",
      consumes = { MediaType.APPLICATION_JSON_VALUE },
      produces = { MediaType.APPLICATION_JSON_VALUE })
  public ResponseEntity<StreamingResponseBody> updateImplementedRequirementPut(
      @PathVariable String id,
      @PathVariable String implementedRequirementId,
      @RequestBody String json) {
    return updateImplementedRequirement(id, implementedRequirementId, json);
  }
}
