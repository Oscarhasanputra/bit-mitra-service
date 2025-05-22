package com.bit.microservices.mitra.model.response;

import jakarta.validation.Valid;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class RequestResponseDTO<T> implements Serializable {


    @Serial
    private static final long serialVersionUID = 6968329017817359295L;

    public RequestResponseDTO(T data){
        this.data = data;
        if (data != null) {
            this.message = "Success";
        } else {
            this.message = "Failed";
        }
    }

    public RequestResponseDTO(String message, T data){
        this.data = data;
        this.message = message;
    }

    private String message;

    @Valid
    private T data;
}
