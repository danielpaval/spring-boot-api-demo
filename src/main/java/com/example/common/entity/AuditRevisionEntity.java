package com.example.common.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import java.util.Date;

@Entity
@Table(name = "revinfo")
@RevisionEntity(AuditRevisionListener.class)
@Data
@EqualsAndHashCode(callSuper = true)
public class AuditRevisionEntity extends DefaultRevisionEntity {

    @Column(name = "username", length = 255)
    private String username;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "revision_date")
    private Date revisionDate;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

}
