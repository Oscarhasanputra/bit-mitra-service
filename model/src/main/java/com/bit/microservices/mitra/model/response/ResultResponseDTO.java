package com.bit.microservices.mitra.model.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@NoArgsConstructor
public class ResultResponseDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 2023719067379316148L;
    private String id;

    private String statusDetail;
    private List<ResponseDetailDTO> responseDetail;

    @JsonIgnore
    private String messageResponseDetail;
}
