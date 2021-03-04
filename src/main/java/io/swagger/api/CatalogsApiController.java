package io.swagger.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;

@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2021-02-22T21:37:05.909Z[GMT]")
@RestController
public class CatalogsApiController implements CatalogsApi {

    private static final Logger log = LoggerFactory.getLogger(CatalogsApiController.class);

    private final ObjectMapper objectMapper;

    private final HttpServletRequest request;

    private RestTemplate restTemplate = new RestTemplate();

    //Pre-load OSCAL Catalog
    static String urlForCatalog = "https://raw.githubusercontent.com/usnistgov/oscal-content/master/nist.gov/SP800-53/rev5/json/NIST_SP-800-53_rev5_catalog.json";
    private String catalogFromUrl = restTemplate.getForObject("https://raw.githubusercontent.com/usnistgov/oscal-content/master/nist.gov/SP800-53/rev5/json/NIST_SP-800-53_rev5_catalog.json", String.class);


    @Autowired
    public CatalogsApiController(ObjectMapper objectMapper, HttpServletRequest request) {
        this.objectMapper = objectMapper;
        this.request = request;
    }

    public ResponseEntity<String> getCatalogById(@Parameter(in = ParameterIn.PATH, description = "ID of catalog to return", required=true, schema=@Schema()) @PathVariable("catalogId") String catalogId) {
        if (catalogId.contains("62f21617-b40f-4e89-bf3b-01b04b68f473")){
            return new ResponseEntity<String>(catalogFromUrl, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<String>("Catalog not found", HttpStatus.BAD_REQUEST);
        }
    }

    public ResponseEntity<String> getCatalogs() {
        return new ResponseEntity<String>(catalogFromUrl, HttpStatus.OK);
    }

    /**
    public ResponseEntity<Void> addCatalog(@Parameter(in = ParameterIn.DEFAULT, description = "OSCAL catalog object to be added", required=true, schema=@Schema()) @Valid @RequestBody OSCALCatalog body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
     **/

    /**
    public ResponseEntity<Void> deleteCatalog(@Parameter(in = ParameterIn.PATH, description = "Catalog id to delete", required=true, schema=@Schema()) @PathVariable("catalogId") Long catalogId,@Parameter(in = ParameterIn.HEADER, description = "" ,schema=@Schema()) @RequestHeader(value="api_key", required=false) String apiKey) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
     **/

    /**
    public ResponseEntity<List<OSCALCatalog>> findCatalogsByName(@NotNull @Parameter(in = ParameterIn.QUERY, description = "Terms to search for in catalog names" ,required=true,schema=@Schema()) @Valid @RequestParam(value = "search-string", required = true) String searchString) {
            try {
                //OSCALCatalog theCatalog = objectMapper.readValue(catalogFromUrl,OSCALCatalog.class);
                Map<String, OSCALCatalog> theCatalog = objectMapper.readValue(catalogFromUrl, new TypeReference<Map<String,OSCALCatalog>>(){});
                System.out.println(theCatalog);
                return new ResponseEntity<List<OSCALCatalog>>(objectMapper.readValue( "[{}]", List.class), HttpStatus.OK);
            }
            catch (IOException e) {
                log.error("Couldn't serialize response for content type application/json", e);
                return new ResponseEntity<List<OSCALCatalog>>(HttpStatus.INTERNAL_SERVER_ERROR);
            }

        //return new ResponseEntity<List<OSCALCatalog>>(HttpStatus.NOT_IMPLEMENTED);
    }
    **/

    /**
    public ResponseEntity<Void> updateCatalog(@Parameter(in = ParameterIn.DEFAULT, description = "Catalog object to be updated", required=true, schema=@Schema()) @Valid @RequestBody OSCALCatalog body) {
        String accept = request.getHeader("Accept");
        return new ResponseEntity<Void>(HttpStatus.NOT_IMPLEMENTED);
    }
     **/
}
