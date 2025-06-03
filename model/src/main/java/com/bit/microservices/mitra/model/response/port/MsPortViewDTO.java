package com.bit.microservices.mitra.model.response.port;

import com.bit.microservices.mitra.model.response.AuditedFieldResponseDTO;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class MsPortViewDTO extends AuditedFieldResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = -7681161353075761631L;

    private String id;
    private String code;
    private String name;
    private String address;
    private String type;
    private String city;
    private String country;
    private String pic;
    private String phoneNumber;
    private Boolean active;
    private String remarks;
    private Boolean deleted;
    private String deletedReason;

    public MsPortViewDTO(String id, String code, String name, String address,
                         String type, String city, String country, String pic,
                         String phoneNumber, Boolean active, String remarks, Boolean deleted, String deletedReason,
                         String createdBy, LocalDateTime createdDate, String modifiedBy, LocalDateTime modifiedDate
                         ){
        super(createdBy,createdDate,modifiedBy,modifiedDate);
        this.id=id;
        this.code=code;
        this.name = name;
        this.address = address;
        this.type=type;
        this.city=city;
        this.country = country;
        this.pic= pic;
        this.phoneNumber=phoneNumber;
        this.active =active;
        this.remarks =remarks;
        this.deleted =deleted;
        this.deletedReason=deletedReason;
    }
}
