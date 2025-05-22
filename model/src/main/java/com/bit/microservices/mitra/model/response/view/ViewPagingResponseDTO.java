package com.bit.microservices.mitra.model.response.view;

import com.bit.microservices.mitra.model.pageable.ImplPageData;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.Serial;
import java.util.List;


@JsonPropertyOrder({"status","code","responseCode","responseMessage","result"})
public class ViewPagingResponseDTO<T> {

    @Serial
    private static final long serialVersionUID = -7697972592943940751L;

    public String status;

    public Integer code;

    public String responseCode;

    public String responseMessage;

    public ImplPageData result;


    public ViewPagingResponseDTO(String status, Integer code, String responseCode, String responseMessage, List<T> content, Pageable pageable, long total) {
//        super(content, pageable, total);
        this.result = new ImplPageData(content,pageable,total);
        this.status=status;
        this.code = code;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }

    public ViewPagingResponseDTO(String status, Integer code, String responseCode, String responseMessage, Page<T> page){

        this.result = new ImplPageData(page.getContent(),page.getPageable(),page.getTotalElements());
        this.status=status;
        this.code = code;
        this.responseCode = responseCode;
        this.responseMessage = responseMessage;
    }



}
