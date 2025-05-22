package com.bit.microservices.mitra.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@JsonPropertyOrder({ "status", "code", "responseCode", "responseMessage", "result" })
@AllArgsConstructor
public class BaseGetResponseDTO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -8299333025509848474L;

    @JsonProperty("status")
    protected String status;

    @JsonProperty("code")
    protected int code;

    @JsonProperty("responseCode")
    private BigDecimal responseCode;

    @JsonProperty("responseMessage")
    private String responseMessage;

    @JsonProperty("result")
    protected T result;
}
