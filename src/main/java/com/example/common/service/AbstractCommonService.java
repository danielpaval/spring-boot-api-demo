package com.example.common.service;

import com.example.common.dto.CommonDto;
import com.example.common.entity.CommonEntity;
import com.example.common.entity.DeletableEntity;
import com.example.common.mapper.CommonMapper;
import com.example.common.repository.CommonRepository;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Optional;
import java.util.Set;

@Slf4j
@RequiredArgsConstructor
public abstract class AbstractCommonService<ID extends Serializable, ENTITY extends CommonEntity<ID>, DTO extends CommonDto<ID>, PATCH_DTO> implements CommonService<ID, ENTITY, DTO, PATCH_DTO> {

    private final CommonRepository<ID, ENTITY> commonRepository;

    private final CommonMapper<ID, ENTITY, DTO, PATCH_DTO> commonMapper;

    private final Validator validator;

    @Override
    @Transactional
    @SneakyThrows
    public DTO save(DTO dto) {
        Set<ConstraintViolation<DTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        ENTITY entity = commonMapper.getEntityClass().getDeclaredConstructor().newInstance();
        commonMapper.update(dto, entity);
        if (entity instanceof DeletableEntity deletableEntity) {
            deletableEntity.setDeleted(false);
        }
        entity = commonRepository.save(entity);
        return commonMapper.map(entity);
    }

    @Override
    public DTO getById(ID id) {
        ENTITY entity = commonRepository.getReferenceById(id);
        return commonMapper.map(entity);
    }

    @Override
    public Optional<DTO> findById(ID id) {
        return commonRepository.findById(id).map(commonMapper::map);
    }

    @Override
    public Page<DTO> findBySpecification(Specification<ENTITY> specification, Pageable pageable) {
        return commonRepository
                .findAll(specification, pageable)
                .map(commonMapper::map);
    }

    @Override
    public Long countBySpecification(Specification<ENTITY> specification) {
        return commonRepository.count(specification);
    }

    @Override
    @Transactional
    public DTO update(ID id, DTO dto) {
        if (id == null) {
            throw new IllegalArgumentException("ID for update cannot be null.");
        }
        Set<ConstraintViolation<DTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        ENTITY entity = commonRepository.getReferenceById(id);
        if (entity instanceof DeletableEntity deletableEntity && deletableEntity.isDeleted()) {
            throw new IllegalStateException("Cannot update a deleted entity with ID: " + id);
        }
        dto.setId(id);
        commonMapper.update(dto, entity);
        if (entity instanceof DeletableEntity deletableEntity) {
            deletableEntity.setDeleted(false);
        }
        ENTITY updatedEntity = commonRepository.save(entity);
        return commonMapper.map(updatedEntity);
    }

    @Override
    public DTO patch(ID id, PATCH_DTO patchDto) {
        ENTITY entity = commonRepository.getReferenceById(id);
        if (entity instanceof DeletableEntity deletableEntity && deletableEntity.isDeleted()) {
            throw new IllegalStateException("Cannot update a deleted entity with ID: " + id);
        }
        commonMapper.patch(patchDto, entity);
        DTO dto = commonMapper.map(entity);
        Set<ConstraintViolation<DTO>> violations = validator.validate(dto);
        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
        if (entity instanceof DeletableEntity deletableEntity) {
            deletableEntity.setDeleted(false);
        }
        ENTITY updatedEntity = commonRepository.save(entity);
        return commonMapper.map(updatedEntity);
    }

    @Override
    @Transactional
    public void deleteById(ID id) {
        ENTITY entity = commonRepository.getReferenceById(id);
        if (entity instanceof DeletableEntity deletableEntity) {
            deletableEntity.setDeleted(true);
            commonRepository.save(entity);
        } else {
            commonRepository.deleteById(id);
        }
    }

}
