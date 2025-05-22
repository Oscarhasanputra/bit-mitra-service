package com.bit.microservices.mitra.exception;

import com.bit.microservices.mitra.model.utils.ConstructResponseCode;
import com.bit.microservices.model.ResultStatus;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;

@Getter
@ResponseStatus(value = HttpStatus.NOT_ACCEPTABLE)
@Slf4j
public class MissingHeaderException extends ResponseStatusException {

    private final BaseGetResponseDTO<HashMap<String, Object>> responseSingle;

    private static final ResponseCodeMessageEnum responseCodeMessageEnum = ResponseCodeMessageEnum.FAILED_FLOW_ID;

    public MissingHeaderException(String headerName, ModuleCodeEnum module, CrudCodeEnum crud) {
        super(HttpStatus.NOT_ACCEPTABLE);
        this.responseSingle = new BaseGetResponseDTO<>();
        this.responseSingle.setStatus(ResultStatus.FAILED.getValue());
        this.responseSingle.setCode(ResultStatus.FAILED.ordinal());
        this.responseSingle.setResponseCode(
                ConstructResponseCode.constructResponseCode(
                        module,
                        crud,
                        responseCodeMessageEnum
                )
        );
        this.responseSingle.setResponseMessage(responseCodeMessageEnum.getMessage(headerName));
        this.responseSingle.setResult(new HashMap<>());
    }

}
