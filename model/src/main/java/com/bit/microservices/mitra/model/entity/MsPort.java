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
            this.id= String.join("~",this.code,UUID.randomUUID().toString());
        }
    }

    @Column(name = "code",columnDefinition = "varchar(255)",nullable = false)
    private String code;

    @Column(name = "name",columnDefinition = "varchar(255)",nullable = false)
    private String name;

    @Column(name = "address",columnDefinition = "varchar(255)",nullable = false)
    private String address="";


    @Enumerated(EnumType.STRING)
    @Column(name = "type",columnDefinition = "varchar(255)",nullable = false)
    private PortTypeEnum type;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ms_city_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private MsCity msCity;


    @Column(name = "ms_city_id",columnDefinition = "varchar(255)",nullable = false)
    private String cityId;

    @Column(name = "ms_city_Code",columnDefinition = "varchar(255)",nullable = false)
    private String cityCode;

    @ManyToOne
    @JoinColumns({
            @JoinColumn(name = "ms_country_id", referencedColumnName = "id", insertable = false, updatable = false)
    })
    private MsCountry msCountry;

    @Column(name = "ms_country_id",columnDefinition = "varchar(255)",nullable = false)
    private String countryId;

    @Column(name = "ms_country_code",columnDefinition = "varchar(255)",nullable = false)
    private String countryCode;

    @Column(name = "pic",columnDefinition = "varchar(255)",nullable = false)
    private String pic;

    @Column(name = "phone_number",columnDefinition = "varchar(255)",nullable = false)
    private String phoneNumber;

    @Column(name = "is_active",columnDefinition = "boolean",nullable = false)
    private Boolean isActive = true;

    @Column(name = "remarks",columnDefinition = "varchar(255)",nullable = true)
    private String remarks="";

    @Column(name = "is_deleted",columnDefinition = "boolean",nullable = false)
    private Boolean isDeleted = false;

    @Column(name = "deleted_reason",columnDefinition = "varchar(255)",nullable = false)
    private String deletedReason = "";


}
