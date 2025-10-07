package com.example.demo.config;

import org.openapitools.jackson.nullable.JsonNullableModule;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ObjectMapperConfiguration {

    @Bean
    public ObjectMapperProvider springdocObjectMapperProvider(SpringDocConfigProperties springDocConfigProperties) {
        ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider(springDocConfigProperties);
        objectMapperProvider.jsonMapper().registerModule(jsonNullableModule());
        return objectMapperProvider;
    }

    @Bean
    public JsonNullableModule jsonNullableModule() {
        return new JsonNullableModule();
    }

}