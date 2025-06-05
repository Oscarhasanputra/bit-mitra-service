package com.bit.microservices.mitra.command.account.impl;

import com.bit.microservices.mitra.command.account.ActivateMsAccountCommand;
import com.bit.microservices.mitra.model.constant.CrudCodeEnum;
import com.bit.microservices.mitra.model.constant.ModuleCodeEnum;
import com.bit.microservices.mitra.model.request.ActivateRequestDTO;
import com.bit.microservices.mitra.model.request.MandatoryHeaderRequestDTO;
import com.bit.microservices.mitra.model.response.BaseResponseDTO;
import com.bit.microservices.mitra.repository.MsAccountRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@Slf4j
public class ActivateMsAccountCommandImpl implements ActivateMsAccountCommand {
    @Autowired
    private MsAccountRepository msAccountRepository;

    @Override
    @Transactional
    public List<BaseResponseDTO> execute(List<ActivateRequestDTO> requests, ModuleCodeEnum module, CrudCodeEnum crud, MandatoryHeaderRequestDTO mandatoryHeaderRequestDTO) {
        List<BaseResponseDTO> errorList = new ArrayList<>();
        List<BaseResponseDTO> responseList = new ArrayList<>();
        Set<String> codeSavedlist = new HashSet<>();
        for (ActivateRequestDTO request : requests) {
            this.msAccountRepository.findById(request.getId());

        }

        return null;

    }
}
