package com.bit.microservices.mitra.controller;

import com.bit.microservices.mitra.command.bank.CreateMsBankCommand;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.command.port.CreatePortCommand;
import com.bit.microservices.mitra.command.port.DeletePortCommand;
import com.bit.microservices.mitra.command.port.UpdatePortCommand;
import com.bit.microservices.mitra.command.port.reactive.*;
import com.bit.microservices.mitra.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.request.*;
import com.bit.microservices.mitra.model.request.bank.MsBankCreateRequestDTO;
import com.bit.microservices.mitra.model.request.port.CreatePortRequestDTO;
import com.bit.microservices.mitra.model.request.port.UpdatePortRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.response.MainResponseDTO;
import com.bit.microservices.mitra.model.response.port.MsPortViewDTO;
import com.bit.microservices.mitra.model.response.view.ViewMainResponseDTO;
import com.bit.microservices.mitra.model.utils.ConstructResponseCode;
import com.bit.microservices.model.ResultStatus;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/port/v1/0")
public class PortController {

    private static final ModuleCodeEnum MODULE= ModuleCodeEnum.PORT;
    @Autowired
    private CommandExecutor commandExecutor;

    @PostMapping("/create")
    @Operation(summary = "", description = "", tags = {"Port"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> createPort(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<CreatePortRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return ReactiveSecurityContextHolderData.assignContextData(
                        commandExecutor.executeAsReactive(CreatePortCommand.class, requestDTO, MODULE, CrudCodeEnum.CREATE_CODE,mandatoryHeaderRequestDTO)
                                .map(response -> {

                                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                                })
                )
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/update")
    @Operation(summary = "", description = "", tags = {"Port"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> updatePort(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<UpdatePortRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return ReactiveSecurityContextHolderData.assignContextData(
                        commandExecutor.executeReactive(UpdatePortCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.UPDATE_CODE,mandatoryHeaderRequestDTO)
                                .map(response -> {

                                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                                })
                )
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/delete")
    @Operation(summary = "", description = "", tags = {"Port"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> deletePort(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<DeleteRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return ReactiveSecurityContextHolderData.assignContextData(
                        commandExecutor.executeReactive(DeletePortCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.UPDATE_CODE,mandatoryHeaderRequestDTO)
                                .map(response -> {

                                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                                })
                )
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/activate")
    @Operation(summary = "", description = "", tags = {"Port"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> activatePort(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<ActivateRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return ReactiveSecurityContextHolderData.assignContextData(
                        commandExecutor.executeReactive(ActivatePortCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.UPDATE_CODE,mandatoryHeaderRequestDTO)
                                .map(response -> {
                                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                                })
                )
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Operation(summary = "Get Single Role", description = "Get Single Data of Role", tags = { "Role" } )
    @PostMapping("/v1/0/get")
    public Mono<ResponseEntity<ViewMainResponseDTO<MsPortViewDTO>>> getMsPort(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @Valid @RequestBody GetSingleRequestDTO requests
    ){
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return
                this.commandExecutor.executeReactive(GetPortCommandReactive.class,requests, MODULE, CrudCodeEnum.GET_CODE,mandatoryHeaderRequestDTO)
                        .map((response)->{
                            return ResponseEntity.ok(response);
                        })
                        .subscribeOn(Schedulers.boundedElastic());
    }

    @Operation(summary = "Get List Role", description = "Get List of Role", tags = { "Role" } )
    @PostMapping("/v1/0/get-list")
    public Mono<ResponseEntity<BaseGetResponseDTO<CustomPageImpl<MsPortViewDTO>>>> getListUserRole(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody SearchRequestDTO requests
    ){

        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);

        return
                this.commandExecutor.executeReactive(GetListPortCommandReactive.class,requests, MODULE, CrudCodeEnum.GETLIST_CODE,mandatoryHeaderRequestDTO)
                        .map((response)->{
                            return ResponseEntity.ok(
                                    new BaseGetResponseDTO<>(
                                            ResultStatus.SUCCESS.getValue(),
                                            ResultStatus.SUCCESS.ordinal(),
                                            ConstructResponseCode.constructResponseCode(MODULE, CrudCodeEnum.GETLIST_CODE, ResponseCodeMessageEnum.SUCCESS),
                                            ResponseCodeMessageEnum.SUCCESS.getMessage(),
                                            response
                                    )
                            );
                        })
                        .subscribeOn(Schedulers.boundedElastic());
    }
}
