package com.bit.microservices.mitra.model.response;

import com.bit.microservices.utils.OffsetDateTimeSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Setter
@Getter
public class AuditedResponseFieldDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8354786918557491711L;


    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime createdDate;

    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    private OffsetDateTime modifiedDate;

    private String createdBy;

    private String modifiedBy;
}
