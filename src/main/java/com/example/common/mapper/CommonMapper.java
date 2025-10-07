package com.example.common.mapper;


import com.example.common.dto.CommonDto;
import com.example.common.entity.CommonEntity;
import org.mapstruct.MappingTarget;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

public interface CommonMapper<ID extends Serializable, ENTITY extends CommonEntity<ID>, DTO extends CommonDto<ID>, PATCH_DTO> {

    DTO map(ENTITY entity);

    void update(DTO dto, @MappingTarget ENTITY entity);

    void patch(PATCH_DTO patchDto, @MappingTarget ENTITY entity);

    @SuppressWarnings("unchecked")
    default Class<ENTITY> getEntityClass() {
        ParameterizedType parameterizedType;
        if (this.getClass().getInterfaces().length > 0) {
            parameterizedType = (ParameterizedType) this.getClass().getInterfaces()[0].getGenericInterfaces()[0];
        } else {
            parameterizedType = (ParameterizedType) this.getClass().getSuperclass().getGenericInterfaces()[0];
        }
        return (Class<ENTITY>) parameterizedType.getActualTypeArguments()[1];
    }

}
