package com.easydynamics.oscalrestservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Main Spring Boot Application class.
 */
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class OscalRestServiceApplication {

  private static final String PROPERTY_CORS_ALLOWED_ORIGINS = "cors.allowedOrigins";

  public static void main(String[] args) {
    SpringApplication.run(OscalRestServiceApplication.class, args);
  }

  @Autowired
  private Environment env;

  /**
   * Defines an MVC configurer that will load allowed CORS origins
   * from environment / application.properties
   *
   * @return the CORS configured configurer
   */
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        String urls = env.getProperty(PROPERTY_CORS_ALLOWED_ORIGINS);
        registry.addMapping("/oscal/v1/**").allowedOrigins(urls.split(",")).allowedMethods("*");
      }
    };
  }
}
