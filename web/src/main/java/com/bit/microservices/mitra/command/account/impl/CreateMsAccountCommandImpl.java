package com.bit.microservices.mitra.command.account.impl;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.mitra.command.account.CreateMsAccountCommand;
import com.bit.microservices.mitra.command.global.reactive.AbstractMitraCommand;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.exception.MetadataCollectibleException;
import com.bit.microservices.mitra.mapper.MsAccountMapper;
import com.bit.microservices.mitra.mapper.MscOwnerMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.entity.MsAccount;
import com.bit.microservices.mitra.model.entity.MscOwner;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.account.MsAccountCreateRequestDTO;
import com.bit.microservices.mitra.model.request.mscowner.MscOwnerCreateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsAccountRepository;
import com.bit.microservices.mitra.repository.MsCountryRepository;
import com.bit.microservices.mitra.repository.MscOwnerRepository;
import com.bit.microservices.model.ResponseResultDetailDTO;
import com.bit.microservices.model.ResultStatus;
import lombok.Setter;
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
public class CreateMsAccountCommandImpl extends AbstractMitraCommand implements CreateMsAccountCommand {
    @Autowired
    private MsAccountRepository msAccountRepository;

    @Autowired
    private MscOwnerRepository mscOwnerRepository;

    @Autowired
    private MsAccountMapper msAccountMapper;

    @Autowired
    private MscOwnerMapper mscOwnerMapper;

    @Autowired
    private MsCountryRepository msCountryRepository;

    @Override
    @Transactional
    public List<BaseResponseDTO> execute(List<MsAccountCreateRequestDTO> requests, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        List<BaseResponseDTO> errorList = new ArrayList<>();
        List<BaseResponseDTO> responseList = new ArrayList<>();
        Set<String> codeSavedlist = new HashSet<>();

        for (MsAccountCreateRequestDTO request : requests) {
            try {
                if(codeSavedlist.contains(request.getCode())){
                    throw new MetadataCollectibleException(module,crud, ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");
                }
                codeSavedlist.add(request.getCode());

                MsAccount msAccount = this.msAccountRepository.findByCodeAndIsDeleted(request.getCode(),false).orElse(null);
                if(!Objects.isNull(msAccount)){
                    throw new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_DATA_ALREADY_EXIST,"");
                }

                MsAccount dataSaved = this.msAccountMapper.toEntity(request);
                dataSaved = this.msAccountRepository.persist(dataSaved);

                List<ResponseResultDetailDTO> errorDetailList = new ArrayList<>();
                for (MscOwnerCreateRequestDTO mscOwnerCreateRequestDTO : request.getCreate()) {
                    ResponseResultDetailDTO error = this.createMscOwner(mscOwnerCreateRequestDTO,request,dataSaved,codeSavedlist,module,crud);
                    if(!Objects.isNull(error)){
                        errorDetailList.add(error);
                    }
                }

                if(!errorDetailList.isEmpty()){
                    BaseResponseDTO baseResponseDTO = new BaseResponseDTO(errorDetailList,request.getCode(), ResultStatus.FAILED);
                    throw new MetadataCollectibleException(baseResponseDTO);
                }

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
                if(!Objects.isNull(err.getBaseResponseDTO())){
                    errorList.add(err.getBaseResponseDTO());
                }else{
                    BaseResponseDTO errorResponse =  this.operationalFailed(request.getCode(),err.getModuleCodeEnum(),err.getCrudCodeEnum(),err.getResponseCodeMessageEnum(),err.getMessage());
                    errorList.add(errorResponse);
                }
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

    @Transactional
    private ResponseResultDetailDTO createMscOwner(MscOwnerCreateRequestDTO mscOwnerCreateRequestDTO, MsAccountCreateRequestDTO request,MsAccount msAccount, Set<String> codeSavedList, ModuleCodeEnum module, CrudCodeEnum crud){
        try {
            String keyCombine = String.join("-",request.getCode(),mscOwnerCreateRequestDTO.getIdentityNo());
            if(codeSavedList.contains(keyCombine)){
                throw new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");
            }
            codeSavedList.add(keyCombine);

            this.msCountryRepository.findByIdAndCode(mscOwnerCreateRequestDTO.getIdentityIssuerId(),mscOwnerCreateRequestDTO.getIdentityIssuerCode()).orElseThrow(()->{
                return new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_DETAIL_NOT_EXIST,"identityIssuerId/identityIssuerCode");
            });

            MscOwner mscOwnerSaved = this.mscOwnerMapper.toEntity(mscOwnerCreateRequestDTO);

            mscOwnerSaved.setMsAccountId(msAccount.getId());
            this.mscOwnerRepository.persist(mscOwnerSaved);


        }catch (MetadataCollectibleException err){
            return this.operationalDetail(module,crud,err.getResponseCodeMessageEnum(),err.getMessage());
        }

        return null;

    }

}
