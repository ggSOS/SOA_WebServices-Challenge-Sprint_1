package br.com.fiap.chatbotdatabase1.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    @Value("${server.servlet.context-path:/}")
    private String contextPath;

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ChatBot DataBase BackEnd - API REST")
                        .version("1.0.0")
                        .description(
                                """
                                        ## Colocar README
                                        """)
                        .contact(new Contact()
                                .name("GustavoG")
                                .url("https://github.com/ggSOS")));
    }
}
// todo