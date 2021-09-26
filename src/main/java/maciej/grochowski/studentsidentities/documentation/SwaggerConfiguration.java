package maciej.grochowski.studentsidentities.documentation;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

    @Bean
    public Docket swaggerConfig() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("maciej.grochowski.studentidentities"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails() {
        final String DESCRIPTION = "This is a Spring Boot, Maven project of Students' database, " +
                "which allows you to make CRUD operations over Students and their Addresses.\n" +
                "Each Student has 3 different Addresses - Residential, Permanent & Correspondence.\n" +
                "The project uses Hibernate Criterias to make database requests.";

        return new ApiInfo(
                "Students Identities",
                DESCRIPTION,
                "1.00",
                "https://www.linkedin.com/in/maciej-grochowski-477b62149",
                "Maciej Grochowski",
                "My GitHub",
                "https://github.com/MaciejGrochowski93"
        );
    }
}


