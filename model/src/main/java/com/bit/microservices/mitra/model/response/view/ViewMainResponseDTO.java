package com.bit.microservices.mitra.model.response.view;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViewMainResponseDTO<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -6697659702746559052L;

    private String status;

    private Integer code;

    private Number responseCode;

    private String responseMessage;

    private T result;
}
