package com.bit.microservices.mitra.model.dto.currency;

import lombok.Data;

import java.io.Serial;
import java.io.Serializable;

@Data
public class CurrencyAPIDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1508340757174311461L;

    private String code;

    private String name;
}
