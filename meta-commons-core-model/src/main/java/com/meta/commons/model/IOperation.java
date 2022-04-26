package com.meta.commons.model;

/**
 * @author Xiong Mao
 * @date 2022/04/26 19:49
 **/
public interface IOperation {

    String getCreatedBy();

    void setCreatedBy(String userId);

    String getUpdatedBy();

    void setUpdatedBy(String userId);
}
