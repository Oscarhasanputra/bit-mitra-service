package com.bit.microservices.mitra.model.response.currency;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
public class CurrencyListDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 8879192917158969132L;

    private String id;

    private String code;

    private String name;

    private Boolean isActive;

    private String remarks;

    private Boolean isDeleted;

    private String deletedReason;
    private OffsetDateTime syncDate;


    private OffsetDateTime createdDate;
    private String createdBy;
    private OffsetDateTime modifiedDate;
    private String modifiedBy;

    public CurrencyListDTO(String id, String code, String name, Boolean isActive,
                           String remarks, Boolean isDeleted, String deletedReason,LocalDateTime syncDate,
                           LocalDateTime createdDate, String createdBy, LocalDateTime modifiedDate,
                            String modifiedBy){
        this.id=id;
        this.code = code;
        this.name = name;
        this.isActive = isActive;
        this.remarks =remarks;
        this.isDeleted = isDeleted;
        this.deletedReason =deletedReason;
        this.syncDate= syncDate.atOffset(ZoneOffset.UTC);
        this.createdDate=createdDate.atOffset(ZoneOffset.UTC);
        this.createdBy = createdBy;
        this.modifiedDate = modifiedDate.atOffset(ZoneOffset.UTC);
        this.modifiedBy = modifiedBy;

    }


}
