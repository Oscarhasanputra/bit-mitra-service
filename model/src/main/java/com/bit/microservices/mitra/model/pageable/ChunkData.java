package com.bit.microservices.mitra.model.pageable;

import org.springframework.data.domain.Pageable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class ChunkData<T> implements Serializable,SliceData<T>
{
    private static final long serialVersionUID = 867755909294344406L;
    private final List<T> content = new ArrayList();
    private final Pageable pageable;
    private final long total;

    public ChunkData(List<T> content, Pageable pageable, long total) {
        Assert.notNull(content, "Content must not be null");
        Assert.notNull(pageable, "Pageable must not be null");
        this.content.addAll(content);
        this.pageable = pageable;
        this.total = total;
    }

    public int getNumber() {
        return this.pageable.isPaged() ? this.pageable.getPageNumber() : 0;
    }

    public int getSize() {
        return this.pageable.isPaged() ? this.pageable.getPageSize() : this.content.size();
    }

    public int getNumberOfElements() {
        return (int)this.total;
    }









    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }




    public Iterator<T> iterator() {
        return this.content.iterator();
    }


    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null");
        Stream<T> var10000 = this.stream();
        Objects.requireNonNull(converter);
        Objects.requireNonNull(converter);
        return (List)var10000.map(converter::apply).collect(Collectors.toList());
    }
}

