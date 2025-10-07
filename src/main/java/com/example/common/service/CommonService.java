package com.example.common.service;

import com.example.common.dto.CommonDto;
import com.example.common.entity.CommonEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface CommonService<ID extends Serializable, ENTITY extends CommonEntity<ID>, DTO extends CommonDto<ID>, PATCH_DTO> {

    DTO save(DTO dto);

    DTO getById(ID id);

    Optional<DTO> findById(ID id);

    Page<DTO> findBySpecification(Specification<ENTITY> specification, Pageable pageable);

    @Transactional(readOnly = true)
    default List<DTO> findAllBySpecification(Specification<ENTITY> specification) {
        return findBySpecification(specification, Pageable.unpaged()).getContent();
    }

    Long countBySpecification(Specification<ENTITY> specification);

    DTO update(ID id, DTO dto);

    DTO patch(ID id, PATCH_DTO dto);

    void deleteById(ID id);

}
