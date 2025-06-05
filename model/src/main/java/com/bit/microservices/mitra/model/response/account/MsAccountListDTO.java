package com.bit.microservices.mitra.model.response.account;

import com.bit.microservices.mitra.model.response.AuditedFieldResponseDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class MsAccountListDTO extends AuditedFieldResponseDTO implements Serializable {


    @Serial
    private static final long serialVersionUID = -3892491907390596711L;

    private String id;
    private String code;
    private String name;
    private Boolean activate;
    private String remarks;
    private Boolean deleted;
    private String deletedReason;
    public MsAccountListDTO(String id, String code, String name, Boolean activate,
                            String remarks, Boolean deleted, String deletedReason,
                            String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate
    ){
        super(createdBy,createdDate,modifiedBy,modifiedDate);
        this.id= id;
        this.code = code;
        this.name = name;
        this.activate=activate;
        this.remarks = remarks;
        this.deleted =deleted;
        this.deletedReason = deletedReason;
    }
}
