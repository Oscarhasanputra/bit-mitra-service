package com.bit.microservices.mitra.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@EqualsAndHashCode(callSuper=false)
@EntityListeners({AuditingEntityListener.class})
@Audited
@Entity
@Table(name = "ms_mitra")
public class MsMitra extends AuditField implements Serializable {

    @Serial
    private static final long serialVersionUID = -1412842633423492789L;
    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;

    @PrePersist
    private void ensureId(){
        if(Objects.isNull(this.id) || this.id.isEmpty()){
            this.id= UUID.randomUUID().toString();
        }
    }

    @Column(name = "code",columnDefinition = "varchar(255)",nullable = false)
    private String code;

    @Column(name = "name",columnDefinition = "varchar(255)",nullable = false)
    private String name;

    @Column(name = "bussiness_entity",columnDefinition = "varchar(255)",nullable = false)
    private String bussinessEntity;

    @Column(name = "taxidentity_type",columnDefinition = "varchar(255)",nullable = false)
    private String taxidentityType;

    @Column(name = "taxidentity_no",columnDefinition = "varchar(255)",nullable = false)
    private String taxidentityNo;

    @Column(name = "ms_account_id",columnDefinition = "varchar(255)",nullable = false)
    private String msAccountId;

    @Column(name = "ms_account_code",columnDefinition = "varchar(255)",nullable = false)
    private String msAccountCode;

    @Column(name = "is_active",columnDefinition = "boolean",nullable = false)
    private Boolean isActive=true;

    @Column(name = "remarks",columnDefinition = "varchar(255)",nullable = false)
    private String remarks="";

    @Column(name = "is_deleted",columnDefinition = "boolean",nullable = false)
    private Boolean isDeleted=false;

    @Column(name = "deleted_reason",columnDefinition = "varchar(255)",nullable = false)
    private String deletedReason="";

}
