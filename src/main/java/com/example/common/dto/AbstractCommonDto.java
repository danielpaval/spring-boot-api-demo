package com.example.common.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Getter
@Setter
@EqualsAndHashCode(of = {"id"})
@SuperBuilder
@NoArgsConstructor
public abstract class AbstractCommonDto<T extends Serializable> implements CommonDto<T> {

    private T id;

}
