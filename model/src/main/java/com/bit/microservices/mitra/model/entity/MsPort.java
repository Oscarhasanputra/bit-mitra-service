package com.bit.microservices.mitra.model.entity;


import com.bit.microservices.mitra.model.constant.msport.PortTypeEnum;
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
@Table(name = "ms_port")
public class MsPort extends AuditField implements Serializable {

    @Serial
    private static final long serialVersionUID = 4379640570951124105L;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "type",columnDefinition = "varchar(255)",nullable = false)
    private PortTypeEnum type;

    @Column(name = "city",columnDefinition = "varchar(255)",nullable = false)
    private String city;

    @Column(name = "country",columnDefinition = "varchar(255)",nullable = false)
    private String country;

    @Column(name = "pic",columnDefinition = "varchar(255)",nullable = false)
    private String pic;

    @Column(name = "phone_number",columnDefinition = "varchar(255)",nullable = false)
    private String phoneNumber;

    @Column(name = "is_active",columnDefinition = "boolean",nullable = false)
    private Boolean isActive = true;

    @Column(name = "remarks",columnDefinition = "varchar(255)",nullable = false)
    private String remarks="";

    @Column(name = "is_deleted",columnDefinition = "boolean",nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "deleted_reason",columnDefinition = "varchar(255)",nullable = false)
    private String deletedReason = "";


}
