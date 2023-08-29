package guru.springframework.sfgrestbrewery.swagger;

import java.util.Collections;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.models.security.SecurityScheme;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.forCodeGeneration(true)
				.securityContexts(Collections.singletonList(securityContext()))
				.securitySchemes(Collections.singletonList(apiKey()))
				.select()
				.apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
				.paths(PathSelectors.regex(SwaggerConstants.SECURE_PATH))
				.build()
				.tags(new springfox.documentation.service.Tag(SwaggerConstants.API_TAG, "All APIs related to Beers"));
	}
	
	private ApiInfo apiInfo() {
		return new ApiInfo(SwaggerConstants.API_TITLE, 
				SwaggerConstants.API_DESCRIPTION, SwaggerConstants.API_VERSION, 
				SwaggerConstants.TERM_OF_SERVICE, contact(), 
				SwaggerConstants.LICENCE, SwaggerConstants.LICENCE_URL, Collections.emptyList());
	}
	
	private Contact contact() {
		return new Contact(SwaggerConstants.CONTACT_NAME, SwaggerConstants.CONTACT_URL, SwaggerConstants.CONTACT_EMAIL);
	}
	
	private ApiKey apiKey() {
		return new ApiKey(SwaggerConstants.SECURITY_REFERENCE, SwaggerConstants.AUTHORIZATION, SecurityScheme.In.HEADER.name());
	}
	
	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(securityReference()).build();
	}
	
	private List<SecurityReference> securityReference(){
		AuthorizationScope[] authorizationScope = 
			{new AuthorizationScope(SwaggerConstants.AUTHORIZATION_SCOPE, SwaggerConstants.AUTHORIZATION_DESCRIPTION)};
		return Collections.singletonList
				(new SecurityReference(SwaggerConstants.SECURITY_REFERENCE, authorizationScope));
	}

}
