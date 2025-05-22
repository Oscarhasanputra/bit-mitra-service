package com.bit.microservices.mitra.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@JsonPropertyOrder({ "content", "number", "numberOfElements", "totalElements", "totalPages", "size" })
public class CustomPageImpl<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 8964023873505229748L;

    private transient List<T> content;
    private int number;
    private int numberOfElements;
    private long totalElements;
    private int totalPages;
    private int size;

    public CustomPageImpl(Page<T> page) {
        this.content = page.getContent();
        this.number = page.getNumber();
        this.numberOfElements = page.getNumberOfElements();
        this.totalElements = page.getTotalElements();
        this.totalPages = page.getTotalPages();
        this.size = page.getSize();
    }

    public CustomPageImpl(){
        this.content = new ArrayList<>();
        this.number = 0;
        this.numberOfElements = 0;
        this.totalElements = 0;
        this.totalPages = 0;
        this.size = 10;
    }
}