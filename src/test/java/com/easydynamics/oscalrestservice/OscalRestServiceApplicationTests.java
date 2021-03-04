package com.easydynamics.oscalrestservice;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;

@SpringBootTest
class OscalRestServiceApplicationTests {
	
	@Autowired
	SwaggerResourcesProvider swaggerResourcesProvider;

	@Test
	void contextLoads() {
		List<SwaggerResource> resources = swaggerResourcesProvider.get();
		boolean foundStaticOpenApi = false;
		for (SwaggerResource swaggerResource : resources) {
			if (swaggerResource.getUrl() != null && swaggerResource.getUrl().contains("openapi.yaml")) {
				foundStaticOpenApi = true;
				break;
			}
		}
		assertTrue(foundStaticOpenApi, "Could not find the static openapi.yaml Swagger resource");
	}
}
