package io.swagger.api;

import io.swagger.model.OSCALComponent;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.*;
import javax.validation.Valid;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-22T21:37:05.909Z[GMT]")
@RestController
public class ComponentsApiController implements ComponentsApi {

    private static final Logger log = LoggerFactory.getLogger(ComponentsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private RestTemplate restTemplate = new RestTemplate();

    //Pre-load OSCAL Component
    static String urlForComponent = "https://raw.githubusercontent.com/usnistgov/oscal-content/master/examples/component-definition/json/example-component.json";
    private String componentFromUrl = restTemplate.getForObject(urlForComponent, String.class);


    @Autowired
    public ComponentsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> getComponentById(@Parameter(in = ParameterIn.PATH, description = "ID of component to return", required=true, schema=@Schema()) @PathVariable("componentId") String componentId) {
        if (componentId.contains("aabcfa61-c6eb-4979-851f-35b461f6a0ef")){
            return new ResponseEntity<String>(componentFromUrl, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Component not found", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<List<OSCALComponent>> getComponents() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<OSCALComponent>>(objectMapper.readValue("[ { }, { } ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<OSCALComponent>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<OSCALComponent>>(HttpStatus.NOT_IMPLEMENTED);
    }
    /*
    public ResponseEntity<Void> updateComponent(@Parameter(in = ParameterIn.DEFAULT, description = "Component object to be updated", required=true, schema=@Schema()) @Valid @RequestBody OSCALComponent body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
    public ResponseEntity<Void> addComponent(@Parameter(in = ParameterIn.DEFAULT, description = "OSCAL components to be added", required=true, schema=@Schema()) @Valid @RequestBody OSCALComponent body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteComponent(@Parameter(in = ParameterIn.PATH, description = "Component id to delete", required=true, schema=@Schema()) @PathVariable("componentId") Long componentId,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="api_key", required=false) String apiKey) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<OSCALComponent>> findComponentsByName(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Terms to search for in component names" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "search-string", required = true) String searchString) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<OSCALComponent>>(objectMapper.readValue("[ { }, { } ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<OSCALComponent>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<OSCALComponent>>(HttpStatus.NOT_IMPLEMENTED);
    }
*/
}
