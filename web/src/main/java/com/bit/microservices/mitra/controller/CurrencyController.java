package com.bit.microservices.mitra.controller;

import com.bit.microservices.mitra.command.currency.reactive.GetListCurrencyCommandReactive;
import com.bit.microservices.mitra.command.currency.reactive.SyncCurrencyCommandReactive;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.model.CustomPageImpl;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.SearchRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.model.response.MainResponseDTO;
import com.bit.microservices.mitra.model.response.currency.CurrencyListDTO;
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
@RequestMapping("/currency/v1/0")
public class CurrencyController {

    private static ModuleCodeEnum MODULE= ModuleCodeEnum.CURRENCY;

    @Autowired
    private CommandExecutor commandExecutor;


    @PostMapping("/_sync-from-google")
    @Operation(summary = "", description = "", tags = {"Currency"})
    public Mono<ResponseEntity<BaseGetResponseDTO>> syncCurrency(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return commandExecutor.executeReactive(SyncCurrencyCommandReactive.class, null, MODULE, CrudCodeEnum.SYNC_CODE,mandatoryHeaderRequestDTO)
                .map(response -> {
                    log.info(flowId);
                    return ResponseEntity.ok(response);
                })
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }


    @PostMapping("/get-list")
    @Operation(summary = "", description = "", tags = {"Currency"})
    public Mono<ResponseEntity<BaseGetResponseDTO<CustomPageImpl<CurrencyListDTO>>>> getListCurrency(
            @RequestHeader(name = "X-FLOW-ID", required = false) String flowId,
            @RequestHeader(name = "X-VALIDATE-ONLY", required = false) String validateOnly,
            @RequestBody SearchRequestDTO request
    ) {
        MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO = new MandatoryHeaderRequestDTO(flowId,validateOnly);
        return commandExecutor.executeReactive(GetListCurrencyCommandReactive.class, request, MODULE, CrudCodeEnum.GETLIST_CODE,mandatoryHeaderRequestDTO)
                .map(x -> {
                    log.info(flowId);
                    return ResponseEntity.ok(
                            new BaseGetResponseDTO<>(
                                    ResultStatus.SUCCESS.getValue(),
                                    ResultStatus.SUCCESS.ordinal(),
                                    ConstructResponseCode.constructResponseCode(MODULE, CrudCodeEnum.GETLIST_CODE, ResponseCodeMessageEnum.SUCCESS),
                                    ResponseCodeMessageEnum.SUCCESS.getMessage(),
                                    x
                            )
                    );
                })
                .doOnError(unused -> log.error(flowId))
                .subscribeOn(Schedulers.boundedElastic());
    }
}
