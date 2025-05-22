package com.bit.microservices.mitra.model.pageable;

import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

public class ImplPageData<T> extends ChunkData<T> implements PageData<T> {
    private static final long serialVersionUID = 867755909294344406L;
    private long total;


    public ImplPageData(List<T> content, Pageable pageable, long total) {
        super(content, pageable,total);
        this.total = total;
    }


    public ImplPageData(List<T> content) {
        this(content, Pageable.unpaged(), null == content ? 0L : (long)content.size());
    }

    public ImplPageData(){
        this(new ArrayList<>(),Pageable.unpaged(),0);
    }

    public ImplPageData(List<T> content, Pageable pageable) {
        super(content, pageable,content.size());
    }

    public int getTotalPages() {
        return this.getSize() == 0 ? 1 : (int)Math.ceil((double)this.total / (double)this.getSize());
    }

    public long getTotalElements() {
        return this.total;
    }



}

