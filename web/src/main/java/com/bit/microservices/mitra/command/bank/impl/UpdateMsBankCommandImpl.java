package com.bit.microservices.mitra.command.bank.impl;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.mitra.command.bank.UpdateMsBankCommand;
import com.bit.microservices.mitra.command.global.reactive.AbstractMitraCommand;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.exception.BaseException;
import com.bit.microservices.mitra.mapper.MsBankMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.entity.MsBank;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.bank.MsBankUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsBankRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class UpdateMsBankCommandImpl extends AbstractMitraCommand implements UpdateMsBankCommand {

    @Autowired
    private MsBankRepository msBankRepository;

    @Autowired
    private MsBankMapper msBankMapper;


    @Override
    @Transactional
    public List<BaseResponseDTO> execute(List<MsBankUpdateRequestDTO> requests, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        List<BaseResponseDTO> errorList = new ArrayList<>();
        List<BaseResponseDTO> responseList = new ArrayList<>();
        Set<String> codeSavedlist = new HashSet<>();

        for (MsBankUpdateRequestDTO request : requests) {
            try {
                if(codeSavedlist.contains(request.getId())){
                    throw new BaseException(module,crud,ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");
                }
                codeSavedlist.add(request.getId());
                MsBank msBank = this.msBankRepository.findByIdAndIsDeleted(request.getId(),false).orElseThrow(()->{
                    return new BaseException(module,crud,ResponseCodeMessageEnum.FAILED_DATA_NOT_EXIST,"");
                });

                if(!msBank.getIsActive()){
                    throw new BaseException(module,crud,ResponseCodeMessageEnum.FAILED_CANNOT_ACTIVATE,"");
                }

                if(!msBank.getCode().equals(request.getCode())){
                    throw new BaseException(module,crud,ResponseCodeMessageEnum.FAILED_INCONSISTENT_CODE,"");
                }


                MsBank updateMsBankData = this.msBankMapper.updateMsCity(request,msBank);

                this.msBankRepository.merge(updateMsBankData);

                BaseResponseDTO baseResponseDTO = this.operationalSuccess(
                        updateMsBankData.getId(),
                        module,
                        crud,
                        ResponseCodeMessageEnum.SUCCESS,
                        ResponseCodeMessageEnum.SUCCESS.getMessage()
                );
                responseList.add(baseResponseDTO);

            }catch (BaseException err){
                BaseResponseDTO errorResponse =  this.operationalFailed(request.getId(),err.getModuleCodeEnum(),err.getCrudCodeEnum(),err.getResponseCodeMessageEnum(),err.getMessage());
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
                        BaseResponseDTO errorResponse =  this.operationalFailed(request.getId(),module,crud,responseType,message);
                        errorList.add(errorResponse);

                    }else{
                        ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;

                        String message = exception.getMessage();

                        BaseResponseDTO errorResponse =  this.operationalFailed(request.getId(),module,crud,responseType,message);
                        errorList.add(errorResponse);


                    }
                }else{
                    ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;
                    String message = e.getCause().getMessage();
                    BaseResponseDTO errorResponse =  this.operationalFailed(request.getId(),module,crud,responseType,message);
                    errorList.add(errorResponse);
                }


            }
            catch (Exception err){
                ExceptionPrinter printer = new ExceptionPrinter(err);

                log.info("Error : {}",printer.getMessage());
                ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;
                String message = err.getMessage();
                BaseResponseDTO errorResponse =  this.operationalFailed(request.getId(),module,crud,responseType,message);
                errorList.add(errorResponse);
            }
        }
        if(!errorList.isEmpty()){
            throw new BadRequestException(errorList);
        }

        return responseList;
    }
}
