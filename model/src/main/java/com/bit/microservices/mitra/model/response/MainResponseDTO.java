package com.bit.microservices.mitra.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainResponseDTO<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = -7411022807110072555L;
    private String status;

    private Integer code;

    private T result;
}
