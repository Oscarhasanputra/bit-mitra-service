package com.bit.microservices.mitra.model.dto.country;

import jakarta.persistence.Column;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class CountryDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -4843097592833458221L;

    private String id;

    private String name;

    private String code;


    private Boolean isActive ;


    private String remarks;


    private Boolean isDeleted;


    private String deletedReason;

    private String createdBy;

    private LocalDateTime createdDate;

    private String modifiedBy;

    private LocalDateTime modifiedDate;
}
