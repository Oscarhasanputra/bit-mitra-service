package com.bit.microservices.mitra.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditedFieldDTO {

    protected String createdBy;

    protected LocalDateTime createdDate;

    protected String modifiedBy;

    protected LocalDateTime modifiedDate;
}
