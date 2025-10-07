package com.example.common.repository;

import com.example.common.entity.CommonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface CommonRepository<ID extends Serializable, ENTITY extends CommonEntity<ID>> extends JpaRepository<ENTITY, ID>, JpaSpecificationExecutor<ENTITY> {
}
