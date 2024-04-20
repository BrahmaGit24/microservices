package com.staybook.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;

@SpringBootApplication
/*@ComponentScans({ @ComponentScan("com.eazybytes.accounts.controller") })
@EnableJpaRepositories("com.staybook.accounts.repository")
@EntityScan("com.staybook.accounts.model")*/
@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@OpenAPIDefinition(
		info = @Info(
				title = "Accounts microservice REST API Documentation",
				description = "StayBook Accounts microservice REST API Documentation",
				version = "v1",
				contact = @Contact(
						name = "Brahma",
						email = "brahma@staybook.com",
						url = "https://www.staybook.com"
				),
				license = @License(
						name = "Apache 2.0",
						url = "https://www.staybook.com"
				)
		),
		externalDocs = @ExternalDocumentation(
				description =  "StayBook Accounts microservice REST API Documentation",
				url = "https://www.staybook.com/swagger-ui.html"
		)
)
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
