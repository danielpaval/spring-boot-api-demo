package com.example.common.mapper;

import org.openapitools.jackson.nullable.JsonNullable;
import org.springframework.stereotype.Component;

@Component
public class JsonNullableMapper {

    public <T> JsonNullable<T> wrap(T entity) {
        return JsonNullable.of(entity);
    }

    public <T> T unwrap(JsonNullable<T> jsonNullable) {
        if (jsonNullable == null) {
            return null;
        }
        return jsonNullable.orElse(null);
    }

}
