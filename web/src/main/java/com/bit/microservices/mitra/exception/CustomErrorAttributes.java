package com.bit.microservices.mitra.exception;

import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.utils.ConstructResponseCode;
import com.bit.microservices.model.ResultStatus;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.core.codec.DecodingException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import java.util.*;

@Slf4j
@Component
public class CustomErrorAttributes extends DefaultErrorAttributes {
	public CustomErrorAttributes() {
		super();
	}

	private static final String ERROR_FIELD = "error";

	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {

		Map<String, Object> errorAttributes = super.getErrorAttributes(request, ErrorAttributeOptions.defaults());
		String path = errorAttributes.getOrDefault("path", "").toString();

		Throwable ex = this.getError(request);
		if (ex instanceof MissingHeaderException missingHeaderException) {
			errorAttributes.put(ERROR_FIELD, missingHeaderException.getResponseSingle());
		}else if(ex.getCause() instanceof DecodingException decEx) {
			errorAttributeMismatchType(errorAttributes, path, decEx);
		} else if (ex instanceof BadRequestException badRequestException) {
			errorBadRequest(errorAttributes, badRequestException, path);
		} else if (ex instanceof DatabaseValidationException databaseValidationException) {
			errorValidationDatabase(errorAttributes, databaseValidationException, path);
		} else {
			customErrorHandler(errorAttributes, ex, path, null);
		}
		return errorAttributes;
	}

	private void customErrorHandler(Map<String, Object> errorAttributes, Throwable ex, String path, String wrongException){
		ResponseCodeMessageEnum customError = ResponseCodeMessageEnum.FAILED_CUSTOM;
		BaseGetResponseDTO<HashMap<String, Object>> responseDTO = new BaseGetResponseDTO<>();
		responseDTO.setStatus(ResultStatus.FAILED.getValue());
		responseDTO.setCode(ResultStatus.FAILED.ordinal());
		responseDTO.setResponseCode(
				ConstructResponseCode.constructResponseCode(
						ModuleCodeEnum.getModuleFromPath(path),
						CrudCodeEnum.getCrudCodeEnumFromPath(path),
						customError
				)
		);
		if (Objects.isNull(ex)){
			responseDTO.setResponseMessage(customError.getMessage(wrongException));
		}else{
			responseDTO.setResponseMessage(customError.getMessage(ex.getMessage()));
		}
		responseDTO.setResult(new HashMap<>());

		errorAttributes.put(ERROR_FIELD, responseDTO);
	}


	private void errorBadRequest(Map<String, Object> errorAttributes, BadRequestException requestErrorException, String path){
		if (!Objects.isNull(requestErrorException.getResponseSingle())){
			errorAttributes.put(ERROR_FIELD, requestErrorException.getResponseSingle());
		}else if (!Objects.isNull(requestErrorException.getResponseList())){
			errorAttributes.put(ERROR_FIELD, requestErrorException.getResponseList());
		}else if (!Objects.isNull(requestErrorException.getResponsePage())){
			errorAttributes.put(ERROR_FIELD, requestErrorException.getResponsePage());
		}else {
			customErrorHandler(errorAttributes, null, path, "Wrong Usage BadRequestException!");
		}
	}

	private void errorValidationDatabase(Map<String, Object> errorAttributes, DatabaseValidationException databaseValidationException, String path){
		if (!Objects.isNull(databaseValidationException.getResponseList())){
			errorAttributes.put(ERROR_FIELD, databaseValidationException.getResponseList());
		}else if (!Objects.isNull(databaseValidationException.getResponseSingle())){
			errorAttributes.put(ERROR_FIELD, databaseValidationException.getResponseSingle());
		}else if (!Objects.isNull(databaseValidationException.getResponsePage())){
			errorAttributes.put(ERROR_FIELD, databaseValidationException.getResponsePage());
		}else{
			customErrorHandler(errorAttributes, null, path, "Wrong Usage DatabaseValidationException!");
		}
	}

	private void errorAttributeMismatchType(Map<String, Object> errorAttributes, String path, DecodingException decEx){
		if (decEx.getCause() instanceof MismatchedInputException jsonException) {
			ResponseCodeMessageEnum responseCodeMessageEnum = ResponseCodeMessageEnum.FAILED_INVALID_TYPE;
			ModuleCodeEnum module = ModuleCodeEnum.getModuleFromPath(path);
			CrudCodeEnum crud = CrudCodeEnum.getCrudCodeEnumFromPath(path);
			String messageError = getMessageError(jsonException, responseCodeMessageEnum);

			BaseGetResponseDTO<HashMap<String, Object>> result = new BaseGetResponseDTO<>();
			result.setStatus(ResultStatus.FAILED.getValue());
			result.setCode(ResultStatus.FAILED.ordinal());
			result.setResponseCode(ConstructResponseCode.constructResponseCode(module, crud, responseCodeMessageEnum));
			result.setResponseMessage(messageError);

			errorAttributes.put(ERROR_FIELD, result);
			errorAttributes.put("status", HttpStatus.UNPROCESSABLE_ENTITY.value());
		}
	}

	private String getMessageError(MismatchedInputException jsonException, ResponseCodeMessageEnum responseCodeMessageEnum) {
		String expectedType = jsonException.getTargetType() != null
				? jsonException.getTargetType().getSimpleName()
				: "Unknown Type";

		String fieldName = "Unknown Field";
		Integer index = null; // Null means it's a single object request
		String messageError = "";

		for (JsonMappingException.Reference ref : jsonException.getPath()) {

			if (ref.getIndex() >= 0) {
				index = ref.getIndex() + 1; // Found an index, means it's a list
			}
			if (ref.getFieldName() != null) {
				fieldName = ref.getFieldName(); // Extract field name
			}
		}

		if (index != null) {
			messageError = responseCodeMessageEnum.getMessage("(expected type " + expectedType +") : " + "'" + fieldName + "' at Element " + index);
		}else{
			messageError = responseCodeMessageEnum.getMessage("(expected type " + expectedType +") : " + "'" + fieldName);
		}
		return messageError;
	}

}
