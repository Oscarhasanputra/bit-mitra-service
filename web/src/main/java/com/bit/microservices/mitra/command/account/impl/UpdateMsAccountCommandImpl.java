package com.bit.microservices.mitra.command.account.impl;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.mitra.command.account.UpdateMsAccountCommand;
import com.bit.microservices.mitra.command.global.reactive.AbstractMitraCommand;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.exception.MetadataCollectibleException;
import com.bit.microservices.mitra.mapper.MsAccountMapper;
import com.bit.microservices.mitra.mapper.MscOwnerMapper;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.constant.mscowner.IdentityTypeEnum;
import com.bit.microservices.mitra.model.entity.MsAccount;
import com.bit.microservices.mitra.model.entity.MscOwner;
import com.bit.microservices.mitra.model.request.IDRequestDTO;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.account.MsAccountCreateRequestDTO;
import com.bit.microservices.mitra.model.request.account.MsAccountUpdateRequestDTO;
import com.bit.microservices.mitra.model.request.mscowner.MscOwnerCreateRequestDTO;
import com.bit.microservices.mitra.model.request.mscowner.MscOwnerUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsAccountRepository;
import com.bit.microservices.mitra.repository.MsCountryRepository;
import com.bit.microservices.mitra.repository.MscOwnerRepository;
import com.bit.microservices.model.ResponseResultDetailDTO;
import com.bit.microservices.model.ResultStatus;
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
public class UpdateMsAccountCommandImpl extends AbstractMitraCommand implements UpdateMsAccountCommand {
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
    public List<BaseResponseDTO> execute(List<MsAccountUpdateRequestDTO> requests, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {

        List<BaseResponseDTO> errorList = new ArrayList<>();
        List<BaseResponseDTO> responseList = new ArrayList<>();
        Set<String> codeSavedlist = new HashSet<>();
        for (MsAccountUpdateRequestDTO request : requests) {
            try {
                if(codeSavedlist.contains(request.getId())){
                    throw new MetadataCollectibleException(module,crud, ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");
                }
                codeSavedlist.add(request.getId());

                MsAccount msAccount = this.msAccountRepository.findByIdAndIsDeleted(request.getId(),false).orElseThrow(()->{
                    return new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_DETAIL_NOT_EXIST,"");
                });

                if(!msAccount.getIsActive().equals(request.getActive())){
                    throw new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_CANNOT_ACTIVATE,"");
                }

                if(!msAccount.getCode().equals(request.getCode())){
                    throw new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_INCONSISTENT_CODE,"");
                }

                MsAccount dataUpdate = this.msAccountMapper.updateMsAccount(request,msAccount);
                this.msAccountRepository.merge(dataUpdate);

                List<ResponseResultDetailDTO> errorDetailList = new ArrayList<>();
                for (IDRequestDTO deleteRequestDTO : request.getDelete()) {
                    ResponseResultDetailDTO error = this.deleteMscOwner(deleteRequestDTO,request,codeSavedlist,module,crud);
                    if(!Objects.isNull(error)){
                        errorDetailList.add(error);
                    }
                }


                for (MscOwnerUpdateRequestDTO mscOwnerUpdateRequestDTO : request.getUpdate()) {
                    ResponseResultDetailDTO error = this.updateMscOwner(mscOwnerUpdateRequestDTO,request,codeSavedlist,module,crud);
                    if(!Objects.isNull(error)){
                        errorDetailList.add(error);
                    }
                }

                for (MscOwnerCreateRequestDTO mscOwnerCreateRequestDTO : request.getCreate()) {
                    ResponseResultDetailDTO error = this.createMscOwner(mscOwnerCreateRequestDTO,request,dataUpdate,codeSavedlist,module,crud);
                    if(!Objects.isNull(error)){
                        errorDetailList.add(error);
                    }
                }


                if(!errorDetailList.isEmpty()){
                    BaseResponseDTO baseResponseDTO = new BaseResponseDTO(errorDetailList,request.getId(), ResultStatus.FAILED);
                    throw new MetadataCollectibleException(baseResponseDTO);
                }

                BaseResponseDTO baseResponseDTO = this.operationalSuccess(
                        request.getId(),
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
                    BaseResponseDTO errorResponse =  this.operationalFailed(request.getId(),err.getModuleCodeEnum(),err.getCrudCodeEnum(),err.getResponseCodeMessageEnum(),err.getMessage());
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

    @Transactional
    private ResponseResultDetailDTO createMscOwner(MscOwnerCreateRequestDTO mscOwnerCreateRequestDTO, MsAccountUpdateRequestDTO request,MsAccount msAccount, Set<String> codeSavedList, ModuleCodeEnum module, CrudCodeEnum crud){
        try {
            String keyCombine = String.join("-",request.getId(),mscOwnerCreateRequestDTO.getIdentityNo(),"CREATE");
            if(codeSavedList.contains(keyCombine)){
                throw new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");
            }

            codeSavedList.add(keyCombine);

            IdentityTypeEnum identityTypeEnum = IdentityTypeEnum.getValue(mscOwnerCreateRequestDTO.getIdentityType());
            MscOwner mscOwner = this.mscOwnerRepository.findByIdentityNoAndIdentityTypeAndIdentityIssuerIdAndMsAccountId(
                    mscOwnerCreateRequestDTO.getIdentityNo(), identityTypeEnum, mscOwnerCreateRequestDTO.getIdentityIssuerId(),msAccount.getId()
            ).orElse(null);

            if(!Objects.isNull(mscOwner)){
                throw new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_CUSTOM,"Duplicate Owner For This Account");
            }

            this.msCountryRepository.findByIdAndCode(mscOwnerCreateRequestDTO.getIdentityIssuerId(),mscOwnerCreateRequestDTO.getIdentityIssuerCode()).orElseThrow(()->{
                return new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_DETAIL_NOT_EXIST,"identityIssuerId/identityIssuerCode");
            });

            MscOwner mscOwnerSaved = this.mscOwnerMapper.toEntity(mscOwnerCreateRequestDTO);
            mscOwnerSaved.setMsAccountId(msAccount.getId());
            this.mscOwnerRepository.persist(mscOwnerSaved);


        }catch (MetadataCollectibleException err){
            return this.operationalDetail(module,crud,err.getResponseCodeMessageEnum(),err.getMessage());
        } catch (Exception e) {
            return this.operationalDetail(module,crud, ResponseCodeMessageEnum.FAILED_CUSTOM,e.getMessage());
        }

        return null;

    }


    @Transactional
    private ResponseResultDetailDTO updateMscOwner(MscOwnerUpdateRequestDTO mscOwnerUpdateRequestDTO, MsAccountUpdateRequestDTO request, Set<String> codeSavedList, ModuleCodeEnum module, CrudCodeEnum crud){
        try {

            String keyCombine = String.join("-",request.getId(),mscOwnerUpdateRequestDTO.getIdentityNo(),"UPDATE");
            if(codeSavedList.contains(keyCombine)){
                throw new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");
            }

            codeSavedList.add(keyCombine);

            MscOwner mscOwner = this.mscOwnerRepository.findById(mscOwnerUpdateRequestDTO.getId()).orElseThrow(()->{
                return new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_DETAIL_NOT_EXIST, mscOwnerUpdateRequestDTO.getId());
            });

            this.msCountryRepository.findByIdAndCode(mscOwnerUpdateRequestDTO.getIdentityIssuerId(),mscOwnerUpdateRequestDTO.getIdentityIssuerCode()).orElseThrow(()->{
                return new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_DETAIL_NOT_EXIST,"identityIssuerId/identityIssuerCode");
            });

            MscOwner dataUpdate = this.mscOwnerMapper.updateMscOwner(mscOwnerUpdateRequestDTO,mscOwner);

            this.mscOwnerRepository.merge(dataUpdate);

        }catch (MetadataCollectibleException err){
            return this.operationalDetail(module,crud,err.getResponseCodeMessageEnum(),err.getMessage());
        } catch (Exception e) {
            return this.operationalDetail(module,crud, ResponseCodeMessageEnum.FAILED_CUSTOM,e.getMessage());
        }

        return null;

    }


    @Transactional
    private ResponseResultDetailDTO deleteMscOwner(IDRequestDTO deleteRequest, MsAccountUpdateRequestDTO request, Set<String> codeSavedList, ModuleCodeEnum module, CrudCodeEnum crud){
        try {

            String keyCombine = String.join("-",request.getId(),deleteRequest.getId(),"DELETE");
            if(codeSavedList.contains(keyCombine)){
                throw new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");
            }

            codeSavedList.add(keyCombine);

            MscOwner mscOwner = this.mscOwnerRepository.findById(deleteRequest.getId()).orElseThrow(()->{
                return new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_DETAIL_NOT_EXIST, deleteRequest.getId());
            });

            this.mscOwnerRepository.delete(mscOwner);

        }catch (MetadataCollectibleException err){
            return this.operationalDetail(module,crud,err.getResponseCodeMessageEnum(),err.getMessage());
        } catch (Exception e) {
            return this.operationalDetail(module,crud, ResponseCodeMessageEnum.FAILED_CUSTOM,e.getMessage());
        }

        return null;

    }

}
