package com.bit.microservices.mitra.model.response.account;

import com.bit.microservices.mitra.model.constant.mscowner.IdentityTypeEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class MscOwnerViewDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = 2141790172571827970L;

    private String id;
    private String name;
    private String identityType;
    private String identityNo;
    private String identityIssuerId;
    private String identityIssuerCode;
    private String mobileNumber;

    public MscOwnerViewDTO(String id, String name, IdentityTypeEnum identityType, String identityNo,
                           String identityIssuerId, String identityIssuerCode,
                           String mobileNumber){
        this.id= id;
        this.name= name;
        this.identityType = identityType.name();
        this.identityNo = identityNo;
        this.identityIssuerId = identityIssuerId;
        this.mobileNumber = mobileNumber;

    }
}
