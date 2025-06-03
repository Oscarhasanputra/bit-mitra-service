package com.bit.microservices.mitra.command.currency.impl;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.mitra.command.currency.CreateCurrencyCommand;
import com.bit.microservices.mitra.command.global.reactive.AbstractMitraCommand;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.exception.MetadataCollectibleException;
import com.bit.microservices.mitra.mapper.MsCurrencyMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.ResponseStatusEnum;
import com.bit.microservices.mitra.model.dto.currency.CurrencyAPIDTO;
import com.bit.microservices.mitra.model.entity.MsCurrency;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.response.BaseGetResponseDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsCurrencyRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

@Component
@Slf4j
public class CreateCurrencyCommandImpl extends AbstractMitraCommand implements CreateCurrencyCommand {
    @Autowired
    private MsCurrencyRepository msCurrencyRepository;

    @Autowired
    private MsCurrencyMapper msCurrencyMapper;


    @Override
    @Transactional
    public List<BaseResponseDTO> execute(List<CurrencyAPIDTO> currencies, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {

        List<BaseResponseDTO> responseList = new ArrayList<>();
        List<BaseResponseDTO> errorList = new ArrayList<>();
        for (CurrencyAPIDTO currency : currencies) {
            try {
                MsCurrency msCurrencyExists = this.msCurrencyRepository.findByCode(currency.getCode()).orElse(null);

                if(Objects.isNull(msCurrencyExists)){

                    MsCurrency msCurrencySaved= this.msCurrencyMapper.toEntity(currency);
                    msCurrencySaved =  this.msCurrencyRepository.persist(msCurrencySaved);


                    BaseResponseDTO baseResponseDTO = this.operationalSuccess(
                            msCurrencySaved.getId(),
                            module,
                            crud,
                            ResponseCodeMessageEnum.SUCCESS,
                            ResponseCodeMessageEnum.SUCCESS.getMessage()
                    );

                    responseList.add(baseResponseDTO);
                }

            }
            catch (MetadataCollectibleException err){
                BaseResponseDTO errorResponse =  this.operationalFailed(currency.getCode(),err.getModuleCodeEnum(),err.getCrudCodeEnum(),err.getResponseCodeMessageEnum(),err.getMessage());
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
                        BaseResponseDTO errorResponse =  this.operationalFailed(currency.getCode(),module,crud,responseType,message);
                        errorList.add(errorResponse);

                    }else{
                        ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;

                        String message = exception.getMessage();

                        BaseResponseDTO errorResponse =  this.operationalFailed(currency.getCode(),module,crud,responseType,message);
                        errorList.add(errorResponse);


                    }
                }else{
                    ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;
                    String message = e.getCause().getMessage();
                    BaseResponseDTO errorResponse =  this.operationalFailed(currency.getCode(),module,crud,responseType,message);
                    errorList.add(errorResponse);
                }


            }
            catch (Exception err){
                ExceptionPrinter printer = new ExceptionPrinter(err);

                log.info("Error : {}",printer.getMessage());
                ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;
                String message = err.getMessage();
                BaseResponseDTO errorResponse =  this.operationalFailed(currency.getCode(),module,crud,responseType,message);
                errorList.add(errorResponse);
            }

        }

        if(!errorList.isEmpty()){
            throw new BadRequestException(errorList);

        }
        return responseList;

    }
}
