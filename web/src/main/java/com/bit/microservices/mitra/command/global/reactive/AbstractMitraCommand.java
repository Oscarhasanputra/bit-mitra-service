package com.bit.microservices.mitra.command.global.reactive;

import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.utils.ConstructResponseCode;
import com.bit.microservices.model.ResponseResultDetailDTO;
import com.bit.microservices.model.ResultStatus;

import java.util.Collections;
import java.util.List;

public class AbstractMitraCommand {

    protected BaseResponseDTO operationalFailed(
            String id,
            ModuleCodeEnum moduleCodeEnum,
            CrudCodeEnum crudCodeEnum,
            ResponseCodeMessageEnum responseCodeMessageEnum,
            String message
    ) {
        BaseResponseDTO result = new BaseResponseDTO(Collections.emptyList(), id, ResultStatus.FAILED);
        ResponseResultDetailDTO detail = this.operationalDetail(moduleCodeEnum, crudCodeEnum, responseCodeMessageEnum, message);
        result.setData(List.of(detail));
        return result;
    }

    protected BaseResponseDTO operationalSuccess(
            String id,
            ModuleCodeEnum moduleCodeEnum,
            CrudCodeEnum crudCodeEnum,
            ResponseCodeMessageEnum responseCodeMessageEnum,
            String message
    ) {
        BaseResponseDTO result = new BaseResponseDTO(Collections.emptyList(), id, ResultStatus.SUCCESS);
        ResponseResultDetailDTO detail = this.operationalDetail(moduleCodeEnum, crudCodeEnum, responseCodeMessageEnum, message);
        result.setData(List.of(detail));
        return result;
    }

    // For Operational Itm / Child
    protected ResponseResultDetailDTO operationalDetail(
            ModuleCodeEnum moduleCodeEnum,
            CrudCodeEnum crudCodeEnum,
            ResponseCodeMessageEnum responseCodeMessageEnum,
            String message
    ) {
        ResponseResultDetailDTO responseDetail = new ResponseResultDetailDTO();
        responseDetail.setMessage(responseCodeMessageEnum.getMessage(message));
        responseDetail.setCode(ConstructResponseCode.constructResponseCode(moduleCodeEnum, crudCodeEnum, responseCodeMessageEnum));

        return responseDetail;
    }
}
