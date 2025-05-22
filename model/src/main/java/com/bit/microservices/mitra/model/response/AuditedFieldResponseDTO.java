package com.bit.microservices.mitra.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Data
@NoArgsConstructor
public class AuditedFieldResponseDTO implements Serializable {


    @Serial
    private static final long serialVersionUID = 6684621256334663260L;

    protected  String createdBy;
    protected OffsetDateTime createdDate;
    protected String modifiedBy;
    protected OffsetDateTime modifiedDate;

    public AuditedFieldResponseDTO(String createdBy, LocalDateTime createdDate, String modifiedBy,LocalDateTime modifiedDate){
        this.createdBy =createdBy;
        this.createdDate = createdDate.atOffset(ZoneOffset.UTC);
        this.modifiedBy = modifiedBy;
        this.modifiedDate = modifiedDate.atOffset(ZoneOffset.UTC);
    }

}
