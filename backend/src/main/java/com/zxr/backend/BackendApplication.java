package com.zxr.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * Main application class
 */
@SpringBootApplication
@ComponentScan(basePackages = "com.zxr")
public class BackendApplication {

	/**
	 * Application entry point.
	 * <p>
	 * Bootstraps the Spring context for the backend application. Keep
	 * arguments minimal in production; use environment variables or
	 * configuration files for runtime configuration.
	 * </p>
	 *
	 * @param args command-line arguments passed to the application
	 */
	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}