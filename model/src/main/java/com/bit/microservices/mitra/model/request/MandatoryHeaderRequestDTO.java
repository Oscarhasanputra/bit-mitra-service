package com.bit.microservices.mitra.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
@AllArgsConstructor
public class MandatoryHeaderRequestDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -3119884713405420391L;

    private String xFlowId;

    private String xValidateOnly;

}
