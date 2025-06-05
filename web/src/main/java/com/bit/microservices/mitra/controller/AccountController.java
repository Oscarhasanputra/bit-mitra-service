package com.bit.microservices.mitra.controller;

import com.bit.microservices.mitra.command.account.CreateMsAccountCommand;
import com.bit.microservices.mitra.command.account.impl.reactive.ActivateMsAccountCommandReactive;
import com.bit.microservices.mitra.command.account.impl.reactive.DeleteMsAccountCommandReactive;
import com.bit.microservices.mitra.command.account.impl.reactive.UpdateMsAccountCommandReactive;
import com.bit.microservices.mitra.command.bank.CreateMsBankCommand;
import com.bit.microservices.mitra.command.bank.reactive.ActivateMsBankCommandReactive;
import com.bit.microservices.mitra.command.bank.reactive.DeleteMsBankCommandReactive;
import com.bit.microservices.mitra.command.bank.reactive.UpdateMsBankCommandReactive;
import com.bit.microservices.mitra.command.executor.Command;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.request.ActivateRequestDTO;
import com.bit.microservices.mitra.model.request.DeleteRequestDTO;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.account.MsAccountCreateRequestDTO;
import com.bit.microservices.mitra.model.request.account.MsAccountUpdateRequestDTO;
import com.bit.microservices.mitra.model.request.bank.MsBankCreateRequestDTO;
import com.bit.microservices.mitra.model.request.bank.MsBankUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.response.MainResponseDTO;
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
@RequestMapping("/account/v1/0")
public class AccountController {

    private ModuleCodeEnum MODULE=ModuleCodeEnum.ACCOUNT;
    @Autowired
    private CommandExecutor commandExecutor;

    @PostMapping("/create")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> createAccount(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<MsAccountCreateRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return ReactiveSecurityContextHolderData.assignContextData(
                        commandExecutor.executeAsReactive(CreateMsAccountCommand.class, requestDTO, MODULE, CrudCodeEnum.CREATE_CODE,mandatoryHeaderRequestDTO)
                                .map(response -> {

                                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                                })
                )
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping("/activate")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> activateAccount(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<ActivateRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return ReactiveSecurityContextHolderData.assignContextData(
                        commandExecutor.executeReactive(ActivateMsAccountCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.ACTIVATE_CODE,mandatoryHeaderRequestDTO)
                                .map(response -> {
                                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                                })
                )
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }


    @PostMapping("/update")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> updateAccount(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<MsAccountUpdateRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return commandExecutor.executeReactive(UpdateMsAccountCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.UPDATE_CODE,mandatoryHeaderRequestDTO)
                .map(response -> {
                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                })
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }
    @PostMapping("/delete")
    @Operation(summary = "", description = "", tags = {"Bank"})
    public Mono<ResponseEntity<MainResponseDTO<List<BaseResponseDTO>>>> deleteAccount(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody List<DeleteRequestDTO> requestDTO
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return commandExecutor.executeReactive(DeleteMsAccountCommandReactive.class, requestDTO, MODULE, CrudCodeEnum.DELETE_CODE,mandatoryHeaderRequestDTO)
                .map(response -> {
                    return ResponseEntity.ok(new MainResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code, response));
                })
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }

}
