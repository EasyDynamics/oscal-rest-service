package com.easydynamics.oscalrestservice.configuration;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * Class to configure Swagger UI to serve a static openapi.yaml specification
 *
 */
@EnableSwagger2
@Configuration
public class SwaggerConfig {
    
    /**
     * Configures a resource provider to serve a static openapi.yaml specification
     * 
     * @return the static configured resource provider
     */
    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider() {
        return () -> {
            SwaggerResource wsResource = new SwaggerResource();
            wsResource.setName("Documentation");
            wsResource.setSwaggerVersion("2.0");
            wsResource.setLocation("/openapi.yaml");
            
            List<SwaggerResource> resources = new ArrayList<SwaggerResource>();
            resources.add(wsResource);
            return resources;
        };
    }
}