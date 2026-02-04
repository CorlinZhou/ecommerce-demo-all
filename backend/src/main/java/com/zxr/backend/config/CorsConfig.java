package com.zxr.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Global CORS configuration.
 * Reads allowed origins from `spring.web.cors.allowed-origins` to keep behavior
 * configurable per-environment (do NOT use "*" when allow-credentials=true).
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

      private static final Logger log = LoggerFactory.getLogger(CorsConfig.class);

      // comma-separated origins from application.yaml or ENV
      @Value("${spring.web.cors.allowed-origins:}")
      private String allowedOriginsProp;

      private List<String> parseOrigins() {
            if (!StringUtils.hasText(allowedOriginsProp))
                  return List.of();
            return Arrays.stream(allowedOriginsProp.split(","))
                        .map(String::trim)
                        .filter(s -> !s.isEmpty())
                        .collect(Collectors.toList());
      }

      @Override
      public void addCorsMappings(CorsRegistry registry) {
            List<String> origins = parseOrigins();
            if (origins.isEmpty()) {
                  log.warn("No CORS allowed-origins configured — CORS requests will be rejected by default.");
                  return;
            }

            log.info("Registering global CORS mapping for origins: {}", origins);

            registry.addMapping("/**")
                        .allowedOrigins(origins.toArray(String[]::new))
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true)
                        .maxAge(3600);
      }
}