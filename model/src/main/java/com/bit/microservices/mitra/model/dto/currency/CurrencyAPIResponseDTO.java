package com.bit.microservices.mitra.model.dto.currency;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class CurrencyAPIResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 8948726625135524069L;

    private List<CurrencyAPIDTO> currencyList;

    @JsonCreator
    public CurrencyAPIResponseDTO(Map<String,String> map){
        this.currencyList = new ArrayList<>();
        for (Map.Entry<String, String> mapObject : map.entrySet()) {
            String key = mapObject.getKey();
            String value = mapObject.getValue();
            CurrencyAPIDTO currencyAPIDTO = new CurrencyAPIDTO();
            currencyAPIDTO.setCode(key);
            currencyAPIDTO.setName(value);
            currencyList.add(currencyAPIDTO);
        }
    }
}
