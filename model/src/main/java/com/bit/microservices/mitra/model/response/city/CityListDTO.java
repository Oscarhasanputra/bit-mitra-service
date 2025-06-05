package com.bit.microservices.mitra.model.response.city;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;

import java.io.Serial;
import java.io.Serializable;
import java.sql.Types;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CityListDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 3792150065759978939L;


    private String id;

    private String code;

    private String name;

    private String provinceCode;

    private Boolean isActive;

    private String remarks;

    private Boolean isDeleted;

    private String deletedReason;
    private OffsetDateTime syncDate;

    private OffsetDateTime createdDate;

    private String createdBy;

    private OffsetDateTime modifiedDate;

    private String modifiedBy;

    public CityListDTO(String id, String code, String name, String provinceCode,Boolean isActive, String remarks, Boolean isDeleted,
                       String deletedReason,LocalDateTime syncDate, LocalDateTime createdDate, String createdBy,
                       LocalDateTime modifiedDate, String modifiedBy){
        this.id=id;
        this.code= code;
        this.name =name;
        this.provinceCode = provinceCode;
        this.syncDate = syncDate.atOffset(ZoneOffset.UTC);
        this.isActive =isActive;
        this.remarks = remarks;
        this.isDeleted = isDeleted;
        this.deletedReason = deletedReason;
        this.createdDate = createdDate.atOffset(ZoneOffset.UTC);
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate.atOffset(ZoneOffset.UTC);
        this.modifiedBy= modifiedBy;


    }
}
