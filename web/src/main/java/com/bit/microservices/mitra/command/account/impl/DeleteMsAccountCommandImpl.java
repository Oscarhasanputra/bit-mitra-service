package com.bit.microservices.mitra.command.account.impl;

import com.bit.microservices.exception.ExceptionPrinter;
import com.bit.microservices.mitra.command.account.DeleteMsAccountCommand;
import com.bit.microservices.mitra.command.global.reactive.AbstractMitraCommand;
import com.bit.microservices.mitra.exception.BadRequestException;
import com.bit.microservices.mitra.exception.MetadataCollectibleException;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.constant.ResponseCodeMessageEnum;
import com.bit.microservices.mitra.model.entity.MsAccount;
import com.bit.microservices.mitra.model.entity.QMscOwner;
import com.bit.microservices.mitra.model.request.DeleteRequestDTO;
import com.bit.microservices.mitra.model.request.IDRequestDTO;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.request.account.MsAccountUpdateRequestDTO;
import com.bit.microservices.mitra.model.request.mscowner.MscOwnerCreateRequestDTO;
import com.bit.microservices.mitra.model.request.mscowner.MscOwnerUpdateRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsAccountRepository;
import com.bit.microservices.model.ResponseResultDetailDTO;
import com.bit.microservices.model.ResultStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.SQLException;
import java.util.*;

@Component
@Slf4j
public class DeleteMsAccountCommandImpl extends AbstractMitraCommand implements DeleteMsAccountCommand {

    @Autowired
    private MsAccountRepository msAccountRepository;

    @Autowired
    private PlatformTransactionManager platformTransactionManager;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public List<BaseResponseDTO> execute(List<DeleteRequestDTO> requests, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        List<BaseResponseDTO> errorList = new ArrayList<>();
        List<BaseResponseDTO> responseList = new ArrayList<>();
        Set<String> codeSavedlist = new HashSet<>();
        for (DeleteRequestDTO request : requests) {
            try {
                if(codeSavedlist.contains(request.getId())){
                    throw new MetadataCollectibleException(module,crud, ResponseCodeMessageEnum.FAILED_CONCURRENCY_DETECTED,"");
                }
                codeSavedlist.add(request.getId());

                MsAccount msAccount = this.msAccountRepository.findByIdAndIsDeleted(request.getId(),false).orElseThrow(()->new MetadataCollectibleException(module,crud,ResponseCodeMessageEnum.FAILED_DATA_NOT_EXIST,""));

                msAccount.setIsDeleted(true);

                this.msAccountRepository.merge(msAccount);
                this.deleteMscOwner(msAccount.getId());



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
    private void deleteMscOwner(String msAccountId){
        TransactionTemplate tx = new TransactionTemplate(platformTransactionManager);
        tx.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);

        long code = tx.execute(status -> {
            JPAQueryFactory queryFactory = new JPAQueryFactory(entityManager);
            QMscOwner qMscOwner = QMscOwner.mscOwner;

            BooleanExpression predicate = qMscOwner.msAccountId.eq(msAccountId);
            return queryFactory.delete(qMscOwner).where(predicate).execute();
        });

        entityManager.clear();
    }
}
