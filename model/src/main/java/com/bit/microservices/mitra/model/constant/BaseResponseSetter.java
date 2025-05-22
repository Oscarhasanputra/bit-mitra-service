package com.bit.microservices.mitra.model.constant;

import com.bit.microservices.model.ResponseResultDetailDTO;

import java.util.List;

public interface BaseResponseSetter<T> {
    public void setId(T value);

    public void setStatus(String status);

    public  void setData(List<ResponseResultDetailDTO> data);


}
