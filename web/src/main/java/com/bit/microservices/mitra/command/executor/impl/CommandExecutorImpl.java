package com.bit.microservices.mitra.command.executor.impl;

import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.command.executor.CommandReactive;
import com.bit.microservices.mitra.exception.MissingHeaderException;
import com.bit.microservices.mitra.model.constant.*;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.utils.ConstructResponseCode;
import com.bit.microservices.model.ResponseResultDetailDTO;
import com.bit.microservices.model.ResultStatus;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
public class CommandExecutorImpl implements CommandExecutor, ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }


    @Override
    public <T, R> T execute(Class<? extends Command<T, R>> commandClass, R request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        Command<T, R> command = applicationContext.getBean(commandClass);

        if(command.isNeedValidateHeader()){
            this.validateHeaderRequest(mandatoryHeaderRequestDTO,module,crud);
        }
        if (command.isNeedValidate(request)) {
            this.validateRequest(request, module, crud);
        }
        return command.execute(request, module, crud,mandatoryHeaderRequestDTO);
    }

    @Override
    public <T, R> Mono<T> executeReactive(Class<? extends CommandReactive<T, R>> commandClass, R request, ModuleCodeEnum module, CrudCodeEnum crud,MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        CommandReactive<T, R> command = applicationContext.getBean(commandClass);
        return Mono.fromCallable(() -> {
                    if(command.isNeedValidateHeader()){
                        this.validateHeaderRequest(mandatoryHeaderRequestDTO,module,crud);
                    }

                    if (command.isNeedValidate(request)) {
                        this.validateRequest(request, module, crud);
                    }
                    return request;
                }).flatMap(req -> command.execute(req, module, crud,mandatoryHeaderRequestDTO))
                .switchIfEmpty(Mono.defer(() -> command.execute(request, module, crud,mandatoryHeaderRequestDTO)));
    }

    private <R> void validateRequest(R request, ModuleCodeEnum module, CrudCodeEnum crud) {
        Validator validator = applicationContext.getBean(Validator.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());


        if (request instanceof List<?> req) {
            List<BaseResponseSetter<?>> responseErrorList = new ArrayList<>();

            for (Object o : req) {

                if(!(o instanceof BaseResponseGetter<?>)){
                    ResponseCodeMessageEnum responseCodeMessageEnum = ResponseCodeMessageEnum.FAILED_CUSTOM;
                    String responseMessage = responseCodeMessageEnum.getMessage("Invalid Developer Code");
                    String responseCode = responseCodeMessageEnum.getHttpStatus()+module.getCode()+crud.getCode()+ResponseCodeMessageEnum.FAILED_FIELD_NOT_EXIST.getCode();
                    BaseResponseSetter<?> responseError = new BaseResponseDTO();
                    responseError.setStatus(ResultStatus.FAILED.getValue());

                    ResponseResultDetailDTO responseDetail = new ResponseResultDetailDTO();
                    responseDetail.setMessage(responseMessage);
                    responseDetail.setCode(new BigDecimal(responseCode));
                    responseError.setData(List.of(responseDetail));
                    throw new BadRequestException(List.of(responseError));
                }

                BaseResponseGetter getter =(BaseResponseGetter) o;

                BaseResponseSetter responseError=getter.buildResponseObject();


//                object.setStatus(ResultStatus.FAILED.getValue());
//                object.setDetailData(errorDetail);
//                BaseResponseDTO responseError = new BaseResponseDTO();
                List<ResponseResultDetailDTO> errorObjects = new ArrayList<>();
                boolean error = false;

                responseError.setId(getter.getResponseId());
                responseError.setStatus(ResultStatus.FAILED.getValue());


                error |= validateConstraints(o, validator, module, crud, errorObjects);
                error |= validateUnknownFields(o, module, crud, errorObjects);

                if (error) {
                    responseError.setData(errorObjects);
                    responseErrorList.add(responseError);
                }
            }

            if (!responseErrorList.isEmpty()) {
                throw new BadRequestException(responseErrorList);
            }
        } else {
            validateSingleRequest(request, validator, module, crud);
        }
    }


    private boolean validateConstraints(Object o, Validator validator, ModuleCodeEnum module, CrudCodeEnum crud, List<ResponseResultDetailDTO> errorObjects) {
        Set<ConstraintViolation<Object>> violations = validator.validate(o);
        if (violations.isEmpty()) return false;

        for (ConstraintViolation<Object> violation : violations) {
            errorObjects.add(createErrorDetail(violation.getPropertyPath().toString(), violation.getMessageTemplate(), module, crud));
        }
        return true;
    }

    private boolean validateUnknownFields(Object o, ModuleCodeEnum module, CrudCodeEnum crud,
                                          List<ResponseResultDetailDTO> errorObjects) {
        if (o instanceof FilterUnknownFields filterUnknownFields && filterUnknownFields.hasUnknownFields()) {
            filterUnknownFields.getUnknownFields().forEach((key, value) ->
                    errorObjects.add(createErrorDetail(key, ResponseCodeMessageEnum.FAILED_FIELD_NOT_EXIST.name(), module, crud))
            );
            return true;
        }
        return false;
    }

    private ResponseResultDetailDTO createErrorDetail(String field, String errorKey, ModuleCodeEnum module, CrudCodeEnum crud) {
        ResponseResultDetailDTO errorDetail = new ResponseResultDetailDTO();
        ResponseCodeMessageEnum responseCodeMessageEnum = getErrorMessage(errorKey);

        errorDetail.setMessage(responseCodeMessageEnum.getMessage(field));
        errorDetail.setCode(ConstructResponseCode.constructResponseCode(module, crud, responseCodeMessageEnum));
        return errorDetail;
    }

    private ResponseCodeMessageEnum getErrorMessage(String key) {
        try {
            return ResponseCodeMessageEnum.valueOf(key);
        } catch (Exception e) {
            return ResponseCodeMessageEnum.FAILED_CUSTOM;
        }
    }

    private <R> void validateSingleRequest(R request, Validator validator, ModuleCodeEnum module, CrudCodeEnum crud) {

        Set<ConstraintViolation<Object>> violations = validator.validate(request);
        if (!violations.isEmpty()) {
            Map<String, String> messageMap = new HashMap<>();

            violations.forEach(v -> {
                String errorKey = v.getMessageTemplate();
                messageMap.put(v.getPropertyPath().toString(), getErrorMessage(errorKey).name());
            });

            throw new BadRequestException(
                    module,
                    crud,
                    ResponseCodeMessageEnum.FAILED_CUSTOM,
                    messageMap.entrySet().stream()
                            .collect(Collectors.groupingBy(
                                    Map.Entry::getValue,
                                    LinkedHashMap::new,
                                    Collectors.mapping(Map.Entry::getKey, Collectors.joining(", "))
                            ))
                            .entrySet().stream()
                            .map(e -> e.getKey() + " : " + e.getValue())
                            .collect(Collectors.joining("; "))
            );
        }

        if (request instanceof FilterUnknownFields filterUnknownFields && !filterUnknownFields.getUnknownFields().isEmpty()) {
            throw new BadRequestException(
                    module,
                    crud,
                    ResponseCodeMessageEnum.FAILED_FIELD_NOT_EXIST,
                    String.join(", ", filterUnknownFields.getUnknownFields().keySet())
            );
        }
    }

    private void validateHeaderRequest(MandatoryHeaderRequestDTO request, ModuleCodeEnum module, CrudCodeEnum crudCodeEnum){

        Boolean isValid = true;

        if(Objects.isNull(request.getXFlowId())){
            isValid = false;
        }else{
            isValid = !request.getXFlowId().isBlank() && !request.getXFlowId().isEmpty();
        }

        if(!isValid){
            throw new MissingHeaderException("X-FLOW-ID",module,crudCodeEnum);
        }

        if(Objects.isNull(request.getXValidateOnly())){
            isValid = false;
        }else{
            isValid = !request.getXValidateOnly().isBlank() && !request.getXValidateOnly().isEmpty();
        }

        if(!isValid){
            throw new MissingHeaderException("X-VALIDATE-ONLY",module,crudCodeEnum);
        }
    }
}
