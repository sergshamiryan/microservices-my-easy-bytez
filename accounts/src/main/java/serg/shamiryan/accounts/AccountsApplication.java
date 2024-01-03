package serg.shamiryan.accounts;

import io.swagger.v3.oas.annotations.ExternalDocumentation;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import serg.shamiryan.accounts.dto.AccountsContactInfoDto;

@SpringBootApplication
@EnableConfigurationProperties(value = {AccountsContactInfoDto.class})
@OpenAPIDefinition(
        info = @Info(
                title = "Account Microservices REST API Documentation",
                description = "ShamiryanBank Accounts microservice REST API Documentation",
                version = "v1",
                contact = @Contact(
                        name = "Sergey Shamiryan",
                        email = "myEmail@gmail.com",
                        url = "www.myWebSite.com"
                )
        ),
        externalDocs = @ExternalDocumentation(
                description = "External doc",
                url = "www.externalDocUrl.com"
        )
)
public class AccountsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AccountsApplication.class, args);
    }

}
