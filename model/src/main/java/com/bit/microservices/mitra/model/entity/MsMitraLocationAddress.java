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
@Table(name = "ms_mitra_locationaddress")
public class MsMitraLocationAddress extends AuditField implements Serializable {

    @Serial
    private static final long serialVersionUID = -773157521795129271L;

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

    @Column(name = "ms_mitra_id",columnDefinition = "varchar(255)",nullable = false)
    private String msMitraId;

    @Column(name = "msMitraCode",columnDefinition = "varchar(255)",nullable = false)
    private String msMitraCode;

    @Column(name = "location_type",columnDefinition = "varchar(255)",nullable = false)
    private String locationType;

    @Column(name = "name",columnDefinition = "varchar(255)",nullable = false)
    private String name;

    @Column(name = "address",columnDefinition = "varchar(255)",nullable = false)
    private String address;

    @Column(name = "country",columnDefinition = "varchar(255)",nullable = false)
    private String country;

    @Column(name = "city",columnDefinition = "varchar(255)",nullable = false)
    private String city;

    @Column(name = "mobile_number",columnDefinition = "varchar(255)",nullable = false)
    private String mobileNumber;


    @Column(name = "longitude",columnDefinition = "varchar(255)",nullable = false)
    private String longitude;

    @Column(name = "latitude",columnDefinition = "varchar(255)",nullable = false)
    private String latitude;

    @Column(name = "province",columnDefinition = "varchar(255)",nullable = false)
    private String province;

//    @Column(name = "distinct",columnDefinition = "varchar(255)",nullable = false)
//    private String distinct;

    @Column(name = "village",columnDefinition = "varchar(255)",nullable = false)
    private String village;

    @Column(name = "oddeven_area",columnDefinition = "boolean",nullable = false)
    private Boolean oddevenArea;

    @Column(name = "maxclass_vehicle",columnDefinition = "boolean",nullable = false)
    private String maxclassVehicle;

    @Column(name = "ms_salesregion_id",columnDefinition = "boolean",nullable = false)
    private String msSalesRegionId;

}
