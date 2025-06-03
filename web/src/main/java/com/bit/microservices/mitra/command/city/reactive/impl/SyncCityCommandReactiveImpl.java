package com.bit.microservices.mitra.command.city.reactive.impl;

import com.bit.microservices.mitra.command.city.CreateCityCommand;
import com.bit.microservices.mitra.command.city.reactive.SyncCityCommandReactive;
import com.bit.microservices.mitra.command.executor.CommandExecutor;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.filter.ReactiveSecurityContextHolderData;
import com.bit.microservices.mitra.http.HttpService;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.dto.city.CityByCountryDTO;
import com.bit.microservices.mitra.model.entity.MsCountry;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.city.CountryIDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsCountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@Slf4j
public class SyncCityCommandReactiveImpl implements SyncCityCommandReactive {

    @Autowired
    private MsCountryRepository msCountryRepository;

    @Autowired
    private HttpService httpService;


    @Autowired
    private CommandExecutor commandExecutor;


    @Override
    public Mono<List<BaseResponseDTO>> execute(CountryIDRequestDTO request, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {

        return Mono.just(request).flatMap((req)->{
            MsCountry msCountry = this.msCountryRepository.findById(request.getCountryId()).orElseThrow(()->{
                return new BadRequestException(module,crud, ResponseCodeMessageEnum.FAILED_DATA_NOT_EXIST,"");
            });

            return this.httpService.getListCityByCountryCode(msCountry.getCode())
                    .flatMap((cityList)->{
                        CityByCountryDTO cityByCountryDTO = new CityByCountryDTO();
                        cityByCountryDTO.setCityList(cityList);
                        cityByCountryDTO.setCountryCode(msCountry.getCode());
                        return ReactiveSecurityContextHolderData.assignContextData(
                            this.commandExecutor.executeAsReactive(CreateCityCommand.class,
                                    cityByCountryDTO,module,crud,mandatoryHeaderRequestDTO)
                        );
            }).onErrorResume((exception)->{
                if(exception instanceof BadRequestException){
                    return Mono.error(exception);
                }else{
                    return Mono.error(new BadRequestException(module,crud,ResponseCodeMessageEnum.FAILED_CUSTOM,exception.getMessage()));
                }
            });
        });
    }
}
