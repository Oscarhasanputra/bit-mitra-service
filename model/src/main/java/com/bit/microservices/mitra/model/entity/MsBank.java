package com.bit.microservices.mitra.model.entity;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.envers.Audited;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "ms_bank")
public class    MsBank extends AuditField implements Serializable
{

    @Id
    @JdbcTypeCode(Types.VARCHAR)
    private String id;
    @PrePersist
    private void ensureId(){
        if(Objects.isNull(this.id) || this.id.isEmpty()){
            this.id= String.join("~",this.code,UUID.randomUUID().toString());
        }
    }

    @Column(name = "code",columnDefinition = "varchar(255)",nullable = false)
    private String code;

    @Column(name = "name",columnDefinition = "varchar(255)",nullable = false)
    private String name;


    @Column(name = "swift_code",columnDefinition = "varchar(255)")
    private String swiftCode="";

    @Column(name = "bi_code",columnDefinition = "varchar(255)")
    private String biCode="";

    @Column(name = "country",columnDefinition = "varchar(255)",nullable = false)
    private String country;

    @Column(name = "is_active",columnDefinition = "boolean",nullable = false)
    private Boolean isActive = true;

    @Column(name = "remarks",columnDefinition = "varchar(255)",nullable = false)
    private String remarks="";

    @Column(name = "is_deleted",columnDefinition = "boolean",nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "deleted_reason",columnDefinition = "varchar(255)",nullable = false)
    private String deletedReason="";

}
