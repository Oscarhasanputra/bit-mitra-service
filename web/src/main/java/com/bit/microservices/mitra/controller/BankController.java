package com.bit.microservices.mitra.controller;

import com.bit.microservices.mitra.command.bank.CreateMsBankCommand;
import com.bit.microservices.mitra.command.bank.DeleteMsBankCommand;
import com.bit.microservices.mitra.command.bank.impl.DeleteMsBankCommandImpl;
import com.bit.microservices.mitra.command.bank.reactive.*;
import com.bit.microservices.mitra.command.city.reactive.SyncCityCommandReactive;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.request.*;
import com.bit.microservices.mitra.model.request.bank.MsBankCreateRequestDTO;
import com.bit.microservices.mitra.model.request.bank.MsBankUpdateRequestDTO;
import com.bit.microservices.mitra.model.request.city.CountryIDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.response.MainResponseDTO;
import com.bit.microservices.mitra.model.response.bank.MsBankViewDTO;
import com.bit.microservices.mitra.model.response.view.ViewMainResponseDTO;
import com.bit.microservices.mitra.model.utils.ConstructResponseCode;
import com.bit.microservices.model.ResultStatus;
import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/bank/v1/0")
public class BankController {

    private final ModuleCodeEnum MODULE= ModuleCodeEnum.CITY;

    @Autowired
    private CommandExecutor commandExecutor;


    @PostMapping("/create")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> createBank(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<MsBankCreateRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return ReactiveSecurityContextHolderData.assignContextData(
                commandExecutor.executeAsReactive(CreateMsBankCommand.class, requestDTO, MODULE, CrudCodeEnum.CREATE_CODE,mandatoryHeaderRequestDTO)
                        .map(response -> {

                            return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                        })
                )
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/activate")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> activateBank(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<ActivateRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return ReactiveSecurityContextHolderData.assignContextData(
                        commandExecutor.executeReactive(ActivateMsBankCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.ACTIVATE_CODE,mandatoryHeaderRequestDTO)
                                .map(response -> {
                                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                                })
                )
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }


    @PostMapping("/update")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> updateBank(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<MsBankUpdateRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return commandExecutor.executeReactive(UpdateMsBankCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.UPDATE_CODE,mandatoryHeaderRequestDTO)
                .map(response -> {
                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                })
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }
    @PostMapping("/delete")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> deleteBank(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<DeleteRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return commandExecutor.executeReactive(DeleteMsBankCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.DELETE_CODE,mandatoryHeaderRequestDTO)
                .map(response -> {
                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                })
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }


    @PostMapping("/get")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<ViewMainResponseDTO<MsBankViewDTO>>> getMsBank(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody GetSingleRequestDTO requestDTO
            ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return commandExecutor.executeReactive(GetMsBankCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.GET_CODE,mandatoryHeaderRequestDTO)
                .map(response -> {
                    return ResponseEntity.ok(response);
                })
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/get-list")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<BaseGetResponseDTO<CustomPageImpl<MsBankViewDTO>>>> getListMsBank(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody SearchRequestDTO searchRequestDTO
            ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return commandExecutor.executeReactive(GetListMsBankCommandReactive.class,searchRequestDTO, MODULE, CrudCodeEnum.GETLIST_CODE,mandatoryHeaderRequestDTO)
                .map(response -> {
                    return ResponseEntity.ok(
                            new BaseGetResponseDTO<>(
                                    ResultStatus.SUCCESS.getValue(),
                                    ResultStatus.SUCCESS.ordinal(),
                                    ConstructResponseCode.constructResponseCode(MODULE, CrudCodeEnum.GETLIST_CODE, ResponseCodeMessageEnum.SUCCESS),
                                    ResponseCodeMessageEnum.SUCCESS.getMessage(),
                                    response
                            ));
                })
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
