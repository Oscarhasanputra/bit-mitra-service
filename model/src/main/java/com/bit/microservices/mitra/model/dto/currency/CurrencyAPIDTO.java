package com.bit.microservices.mitra.model.dto.currency;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyAPIDTO implements Serializable {
    @Serial
    private static final long serialVersionUID = -1508340757174311461L;

    private String code;

    private String name;
}
