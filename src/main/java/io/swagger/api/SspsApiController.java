package io.swagger.api;

import io.swagger.model.OSCALSsp;
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
public class SspsApiController implements SspsApi {

    private static final Logger log = LoggerFactory.getLogger(SspsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private RestTemplate restTemplate = new RestTemplate();

    //Pre-load OSCAL Catalog
    static String urlForSsp = "https://raw.githubusercontent.com/usnistgov/oscal-content/master/examples/ssp/json/ssp-example.json";
    private String sspFromUrl = restTemplate.getForObject(urlForSsp, String.class);



    @Autowired
    public SspsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<OSCALSsp> getSspById(@Parameter(in = ParameterIn.PATH, description = "ID of system security plan to return", required=true, schema=@Schema()) @PathVariable("sspId") Long sspId) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<OSCALSsp>(objectMapper.readValue("{ }", OSCALSsp.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<OSCALSsp>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<OSCALSsp>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<OSCALSsp>> getSsps() {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<OSCALSsp>>(objectMapper.readValue("[ { }, { } ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<OSCALSsp>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<OSCALSsp>>(HttpStatus.NOT_IMPLEMENTED);
    }

    /*
    public ResponseEntity<Void> updateSsp(@Parameter(in = ParameterIn.DEFAULT, description = "System security plan object to be updated", required=true, schema=@Schema()) @Valid @RequestBody OSCALSsp body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> addSsp(@Parameter(in = ParameterIn.DEFAULT, description = "OSCAL system security plan object to be added", required=true, schema=@Schema()) @Valid @RequestBody OSCALSsp body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteSsp(@Parameter(in = ParameterIn.PATH, description = "System security plan id to delete", required=true, schema=@Schema()) @PathVariable("sspId") Long sspId,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="api_key", required=false) String apiKey) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<List<OSCALSsp>> findSspsByName(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Terms to search for in system security plan names" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "search-string", required = true) String searchString) {
        String accept = request.getHeader("Accept");
        if (accept != null && accept.contains("application/json")) {
            try {
                return new ResponseEntity<List<OSCALSsp>>(objectMapper.readValue("[ { }, { } ]", List.class), HttpStatus.NOT_IMPLEMENTED);
            } catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<OSCALSsp>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        return new ResponseEntity<List<OSCALSsp>>(HttpStatus.NOT_IMPLEMENTED);
    }
    */
}
