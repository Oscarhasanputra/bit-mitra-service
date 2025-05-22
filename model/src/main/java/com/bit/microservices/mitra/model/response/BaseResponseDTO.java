package com.bit.microservices.mitra.model.response;

import com.bit.microservices.mitra.model.constant.BaseResponseSetter;
import com.bit.microservices.model.ResponseResultDTO;
import com.bit.microservices.model.ResponseResultDetailDTO;
import com.bit.microservices.model.ResultStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonPropertyOrder({ "id", "statusDetail", "responseDetail" })
public class BaseResponseDTO extends ResponseResultDTO implements BaseResponseSetter<String> {
    @JsonProperty("id")
    private String id;

    public BaseResponseDTO(List<ResponseResultDetailDTO> data){
        super(data);
        this.id = "";
    }

    public BaseResponseDTO(List<ResponseResultDetailDTO> data, ResultStatus status){
        super(data, status);
        this.id = "";
    }

    public BaseResponseDTO(List<ResponseResultDetailDTO> data, String id){
        super(data);
        this.id = id;
    }

    public BaseResponseDTO(List<ResponseResultDetailDTO> data, String id, ResultStatus status){
        super(data, status);
        this.id = id;
    }


}
