//package com.bit.microservices.mitra.command.global.reactive.impl;
//
//import com.bit.microservices.mitra.command.global.reactive.CheckFlowIdCommandReactive;
//import com.bit.microservices.mitra.exception.MissingHeaderException;
//import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
//import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.stereotype.Component;
//import reactor.core.publisher.Mono;
//
//import java.util.Objects;
//
//@Component
//@Slf4j
//public class CheckFlowIdCommandReactiveImpl implements CheckFlowIdCommandReactive {
//    @Override
//    public Mono<Void> execute(String request, ModuleCodeEnum module, CrudCodeEnum crud) {
//        return Mono.fromCallable(() -> {
//            if (Objects.isNull(request) || request.isBlank() || request.isEmpty()) {
//                throw new MissingHeaderException("X-FLOW-ID", module, crud);
//            }
//            return null;
//        });
//    }
//}
