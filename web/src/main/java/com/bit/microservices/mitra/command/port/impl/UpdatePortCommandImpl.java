package com.bit.microservices.mitra.command.port.impl;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.mitra.command.global.reactive.AbstractMitraCommand;
import com.bit.microservices.mitra.command.port.UpdatePortCommand;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.exception.MetadataCollectibleException;
import com.bit.microservices.mitra.mapper.MsPortMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.entity.MsPort;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.port.UpdatePortRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsPortRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class UpdatePortCommandImpl extends AbstractMitraCommand implements UpdatePortCommand {
    @Autowired
    private MsPortRepository msPortRepository;

    @Autowired
    private MsPortMapper msPortMapper;

    @Override
    public List<BaseResponseDTO> execute(List<UpdatePortRequestDTO> requests, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        List<BaseResponseDTO> errorList = new ArrayList<>();
        List<BaseResponseDTO> responseList = new ArrayList<>();
        Set<String> codeSavedlist = new HashSet<>();


        for (UpdatePortRequestDTO request : requests) {
            try {

                if(codeSavedlist.contains(request.getId())){
                    throw new MetadataCollectibleException(module,crud, ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");

                }
                codeSavedlist.add(request.getId());
                MsPort msPort = this.msPortRepository.findByIdAndIsDeleted(request.getId(),false).orElseThrow(()->{
                    throw new MetadataCollectibleException(module,crud, ResponseCodeMessageEnum.FAILED_DATA_NOT_EXIST,"");
                });

                if(!msPort.getCode().equals(request.getCode())){
                    throw new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_INCONSISTENT_CODE,"");
                }

                if(!msPort.getIsActive()){
                    throw new MetadataCollectibleException(module,crud, ResponseCodeMessageEnum.FAILED_CANNOT_ACTIVATE,"");
                }

                MsPort dataUpdated = this.msPortMapper.updateMsPort(request,msPort);
                this.msPortRepository.merge(dataUpdated);

                BaseResponseDTO baseResponseDTO = this.operationalSuccess(
                        dataUpdated.getId(),
                        module,
                        crud,
                        ResponseCodeMessageEnum.SUCCESS,
                        ResponseCodeMessageEnum.SUCCESS.getMessage()
                );
                responseList.add(baseResponseDTO);
            }
            catch (MetadataCollectibleException err){
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
