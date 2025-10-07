package com.example.common.dto;

import java.io.Serializable;

public interface CommonDto<T extends Serializable> {

    T getId();

    void setId(T id);

}
