package com.bit.microservices.mitra.command.port.impl;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.mitra.command.global.reactive.AbstractMitraCommand;
import com.bit.microservices.mitra.command.port.CreatePortCommand;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.exception.MetadataCollectibleException;
import com.bit.microservices.mitra.mapper.MsPortMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.entity.MsPort;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.port.PortCreateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsPortRepository;
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
public class CreatePortCommandImpl extends AbstractMitraCommand implements CreatePortCommand {

    @Autowired
    private MsPortRepository msPortRepository;

    @Autowired
    private MsPortMapper msPortMapper;

    @Override
    @Transactional
    public List<BaseResponseDTO> execute(List<PortCreateRequestDTO> requests, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        List<BaseResponseDTO> errorList = new ArrayList<>();
        List<BaseResponseDTO> responseList = new ArrayList<>();
        Set<String> codeSavedlist = new HashSet<>();

        for (PortCreateRequestDTO request : requests) {
            try {
                if(codeSavedlist.contains(request.getCode())){
                    throw new MetadataCollectibleException(module,crud, ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");
                }

                codeSavedlist.add(request.getCode());

                MsPort msPort = this.msPortRepository.findByCodeAndIsDeleted(request.getCode(),false).orElse(null);
                if(!Objects.isNull(msPort)){
                    throw new MetadataCollectibleException(module,crud, ResponseCodeMessageEnum.FAILED_DATA_ALREADY_EXIST,"");
                }

                MsPort dataSaved = this.msPortMapper.toEntity(request);
                dataSaved = this.msPortRepository.persist(dataSaved);
                BaseResponseDTO baseResponseDTO = this.operationalSuccess(
                        dataSaved.getId(),
                        module,
                        crud,
                        ResponseCodeMessageEnum.SUCCESS,
                        ResponseCodeMessageEnum.SUCCESS.getMessage()
                );
                responseList.add(baseResponseDTO);

            }
            catch (MetadataCollectibleException err){
                BaseResponseDTO errorResponse =  this.operationalFailed(request.getCode(),err.getModuleCodeEnum(),err.getCrudCodeEnum(),err.getResponseCodeMessageEnum(),err.getMessage());
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
                        BaseResponseDTO errorResponse =  this.operationalFailed(request.getCode(),module,crud,responseType,message);
                        errorList.add(errorResponse);

                    }else{
                        ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;

                        String message = exception.getMessage();

                        BaseResponseDTO errorResponse =  this.operationalFailed(request.getCode(),module,crud,responseType,message);
                        errorList.add(errorResponse);


                    }
                }else{
                    ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;
                    String message = e.getCause().getMessage();
                    BaseResponseDTO errorResponse =  this.operationalFailed(request.getCode(),module,crud,responseType,message);
                    errorList.add(errorResponse);
                }


            }
            catch (Exception err){
                ExceptionPrinter printer = new ExceptionPrinter(err);

                log.info("Error : {}",printer.getMessage());
                ResponseCodeMessageEnum responseType = ResponseCodeMessageEnum.FAILED_CUSTOM;
                String message = err.getMessage();
                BaseResponseDTO errorResponse =  this.operationalFailed(request.getCode(),module,crud,responseType,message);
                errorList.add(errorResponse);
            }

        }

        if(!errorList.isEmpty()){
            throw new BadRequestException(errorList);
        }

        return responseList;
    }
}
