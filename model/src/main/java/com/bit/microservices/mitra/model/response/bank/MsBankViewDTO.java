package com.bit.microservices.mitra.model.response.bank;

import com.bit.microservices.mitra.model.response.AuditedFieldResponseDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

@Data
public class MsBankViewDTO extends AuditedFieldResponseDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1842458452794658155L;

    private String id;

    private String code;

    private String name;

    private String swiftCode;

    private String biCode;

    private String country;

    private Boolean active;

    private String remarks;

    private Boolean deleted;

    private String deletedReason;

    public MsBankViewDTO(String id, String code, String name, String swiftCode,
                         String biCode, String country, Boolean active, String remarks,
                         Boolean deleted, String deletedReason, String createdBy, LocalDateTime createdDate,
                         String modifiedBy,LocalDateTime modifiedDate
                         ){
        super(createdBy,createdDate,modifiedBy,modifiedDate);
        this.id = id;
        this.code = code;
        this.name = name;
        this.swiftCode= swiftCode;
        this.biCode = biCode;
        this.country =country;
        this.active =active;
        this.remarks = remarks;
        this.deleted = deleted;
        this.deletedReason = deletedReason;
    }

}
