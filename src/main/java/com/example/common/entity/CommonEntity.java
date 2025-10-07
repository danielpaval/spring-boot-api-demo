package com.example.common.entity;

import java.io.Serializable;

public interface CommonEntity<ID extends Serializable> {

    ID getId();

    void setId(ID id);

}

