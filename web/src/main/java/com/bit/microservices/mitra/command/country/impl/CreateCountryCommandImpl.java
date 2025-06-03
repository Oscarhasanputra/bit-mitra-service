package com.bit.microservices.mitra.command.country.impl;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.mitra.command.country.CreateCountryCommand;
import com.bit.microservices.mitra.command.global.reactive.AbstractMitraCommand;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.exception.MetadataCollectibleException;
import com.bit.microservices.mitra.mapper.MsCountryMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.dto.country.CountryAPIResponseDTO;
import com.bit.microservices.mitra.model.entity.MsCountry;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.city.CountryIDRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsCountryRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.Metadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CreateCountryCommandImpl extends AbstractMitraCommand implements CreateCountryCommand {


    @Autowired
    private MsCountryRepository msCountryRepository;

    @Autowired
    private MsCountryMapper msCountryMapper;

    @Override
    public List<BaseResponseDTO> execute(List<CountryAPIResponseDTO> countryList, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {

        List<BaseResponseDTO> responseList = new ArrayList<>();
        List<BaseResponseDTO> errorList = new ArrayList<>();
        countryList.stream().forEach((country)->{
            try {
                MsCountry msCountry = this.msCountryRepository.findByCodeAndIsDeleted(country.getCode(),false).orElse(null);
                if(Objects.isNull(msCountry)){
                    MsCountry dataSaved = this.msCountryMapper.APIDtoToEntity(country);
                    this.msCountryRepository.persist(dataSaved);
                    BaseResponseDTO baseResponseDTO = this.operationalSuccess(
                            dataSaved.getId(),
                            module,
                            crud,
                            ResponseCodeMessageEnum.SUCCESS,
                            ResponseCodeMessageEnum.SUCCESS.getMessage()
                    );

                    responseList.add(baseResponseDTO);
                }

                log.info("Country : {}",country);
            }
            catch (MetadataCollectibleException err){
                BaseResponseDTO errorResponse =  this.operationalFailed(country.getCode(),err.getModuleCodeEnum(),err.getCrudCodeEnum(),err.getResponseCodeMessageEnum(),err.getMessage());
                errorList.add(errorResponse);
            }
            catch (JpaSystemException | DataIntegrityViolationException e){
                ExceptionPrinter printer = new ExceptionPrinter(e);

                log.info("Error JPA : {}",printer.getMessage());
                Throwable cause = e.getMostSpecificCause();
                if(!Objects.isNull(cause) && cause instanceof SQLException){
                    SQLException exception = (SQLException) cause;
                    String sqlState = exception.getSQLState();
                    if(sqlState.equals("23505")){
                        //duplicate data
                        ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_DATA_ALREADY_EXIST;
                        String message = exception.getMessage();
                        BaseResponseDTO errorResponse =  this.operationalFailed(country.getCode(),module,crud,responseType,message);
                        errorList.add(errorResponse);

                    }else{
                        ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;

                        String message = exception.getMessage();

                        BaseResponseDTO errorResponse =  this.operationalFailed(country.getCode(),module,crud,responseType,message);
                        errorList.add(errorResponse);


                    }
                }else{
                    ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;
                    String message = e.getCause().getMessage();
                    BaseResponseDTO errorResponse =  this.operationalFailed(country.getCode(),module,crud,responseType,message);
                    errorList.add(errorResponse);
                }


            }
            catch (Exception err){
                ExceptionPrinter printer = new ExceptionPrinter(err);

                log.info("Error : {}",printer.getMessage());
                ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;
                String message = err.getMessage();
                BaseResponseDTO errorResponse =  this.operationalFailed(country.getCode(),module,crud,responseType,message);
                errorList.add(errorResponse);
            }

        });
        if(!errorList.isEmpty()){
            throw new BadRequestException(errorList);
        }
//        ResponseCodeMessageEnum responseCodeMessageEnum = ResponseCodeMessageEnum.SUCCESS;
//
//        String responseCode = responseCodeMessageEnum.getHttpStatus()+module.getCode()+crud.getCode()+responseCodeMessageEnum.getCode();
//
//        BaseGetResponseDTO responseDTO =  new BaseGetResponseDTO<>(ResponseStatusEnum.SUCCESS.responseMessage, ResponseStatusEnum.SUCCESS.code,new BigDecimal(responseCode),responseCodeMessageEnum.getMessage(),new HashMap<>());

        return responseList;
    }
}
