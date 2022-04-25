package com.meta.commons.model;

import java.io.Serializable;

/**
 * @author Xiong Mao
 * @date 2022/04/24 23:35
 **/
public interface IEntity<ID extends Serializable> extends Serializable {

    ID getId();

    void setId(ID id);
}
