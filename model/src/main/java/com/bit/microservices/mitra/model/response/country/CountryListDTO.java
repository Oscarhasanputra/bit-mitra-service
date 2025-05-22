package com.bit.microservices.mitra.model.response.country;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@AllArgsConstructor
public class CountryListDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -1818721763234251609L;

    private String id;
    private String name;
    private String code;
    private Boolean isActive;
    private String remarks;
    private Boolean isDeleted;
    private String deletedReason;
    private String createdBy;
    private OffsetDateTime createdDate;
    private String modifiedBy;
    private OffsetDateTime modifiedDate;


    public CountryListDTO(String id,String name, String code, Boolean isActive,
                          String remarks, Boolean isDeleted, String deletedReason,
                          String createdBy, LocalDateTime createdDate,String modifiedBy, LocalDateTime modifiedDate){

        this.id = id;
        this.name = name;
        this.code = code;
        this.isActive= isActive;
        this.remarks = remarks;
        this.isDeleted = isDeleted;
        this.deletedReason = deletedReason;
        this.createdBy = createdBy;
        this.createdDate = createdDate.atOffset(ZoneOffset.UTC);
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate.atOffset(ZoneOffset.UTC);
    }

}
