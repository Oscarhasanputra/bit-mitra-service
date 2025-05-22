package com.bit.microservices.mitra.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDetailDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2431106181764252569L;
    private Number responseCode;

    private String responseMessage;
}
