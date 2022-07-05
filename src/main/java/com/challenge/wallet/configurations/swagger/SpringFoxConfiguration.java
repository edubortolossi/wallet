package com.challenge.wallet.configurations.swagger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
@Profile("!test")
public class SpringFoxConfiguration {

  private static final String BASE_PACKAGE_EXTERNAL = "com.challenge.wallet.gateways.inputs.http.external";
  private static final String BASE_PACKAGE_INTERNAL = "com.challenge.wallet.gateways.inputs.http.internal";
  public static final String EXTERNAL_GROUP_NAME = "external";
  public static final String INTERNAL_GROUP_NAME = "internal";

  @Value("${spring.application.name}")
  private String applicationName;

  @Value("${spring.profiles.active}")
  private String profile;

  @Bean
  public Docket external() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_EXTERNAL))
        .paths(PathSelectors.any())
        .build()
        .pathMapping(getPathMapping())
        .groupName(EXTERNAL_GROUP_NAME)
        .useDefaultResponseMessages(false)
        .apiInfo(getExternalApiInfo());
  }

  @Bean
  public Docket internal() {
    return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage(BASE_PACKAGE_INTERNAL))
        .paths(PathSelectors.any())
        .build()
        .pathMapping(getPathMapping())
        .groupName(INTERNAL_GROUP_NAME)
        .useDefaultResponseMessages(false)
        .apiInfo(getInternalApiInfo());
  }

  private ApiInfo getExternalApiInfo() {
    return new ApiInfoBuilder()
        .title("Wallet API")
        .version("1")
        .build();
  }

  private ApiInfo getInternalApiInfo() {
    return new ApiInfoBuilder()
        .title("Wallet API")
        .description("Internal APIs")
        .version("1")
        .build();
  }

  private String getPathMapping() {
    String pathMapping = "";
    if (!profile.equalsIgnoreCase("local")) {
      return pathMapping.concat(applicationName);
    }
    return pathMapping;
  }
}
