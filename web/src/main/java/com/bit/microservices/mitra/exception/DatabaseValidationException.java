package com.bit.microservices.mitra.exception;

import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.utils.ConstructResponseCode;
import com.bit.microservices.model.ResponseDTO;
import com.bit.microservices.model.ResponseResultDTO;
import com.bit.microservices.model.ResultStatus;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;

@Getter
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DatabaseValidationException extends ResponseStatusException {

    private final ResponseDTO<? extends ResponseResultDTO> responseList;
    private final BaseGetResponseDTO<HashMap<String, Object>> responseSingle;
    private final BaseGetResponseDTO<CustomPageImpl<?>> responsePage;

    public DatabaseValidationException(List<BaseResponseDTO> responseList) {
        super(HttpStatus.BAD_REQUEST);
        this.responseSingle = null;
        this.responsePage = null;
        this.responseList = new ResponseDTO<>(responseList);
        this.responseList.setStatus(ResultStatus.FAILED.getValue());
        this.responseList.setCode(ResultStatus.FAILED.ordinal());
    }

    public DatabaseValidationException(ModuleCodeEnum module, CrudCodeEnum crud, ResponseCodeMessageEnum responseCodeMessageEnum, String message) {
        super(HttpStatus.BAD_REQUEST);
        if (crud.getGroup().equals(CrudCodeEnum.ApiGroupType.NON_ATOMIC)) {
            this.responsePage = null;
            this.responseList = null;
            this.responseSingle = new BaseGetResponseDTO<>();
            this.responseSingle.setStatus(ResultStatus.FAILED.getValue());
            this.responseSingle.setCode(ResultStatus.FAILED.ordinal());
            this.responseSingle.setResponseCode(
                    ConstructResponseCode.constructResponseCode(module, crud, responseCodeMessageEnum)
            );
            this.responseSingle.setResponseMessage(responseCodeMessageEnum.getMessage(message));
            this.responseSingle.setResult(new HashMap<>());
        } else if (crud.getGroup().equals(CrudCodeEnum.ApiGroupType.NON_ATOMIC_PAGE)) {
            this.responseList = null;
            this.responseSingle = null;
            this.responsePage = new BaseGetResponseDTO<>();
            this.responsePage.setStatus(ResultStatus.FAILED.getValue());
            this.responsePage.setCode(ResultStatus.FAILED.ordinal());
            this.responsePage.setResponseCode(
                    ConstructResponseCode.constructResponseCode(module, crud, responseCodeMessageEnum)
            );
            this.responsePage.setResponseMessage(responseCodeMessageEnum.getMessage(message));
            this.responsePage.setResult(new CustomPageImpl<>());
        } else {
            this.responseSingle = null;
            this.responsePage = null;
            this.responseList = null;
        }
    }
}
