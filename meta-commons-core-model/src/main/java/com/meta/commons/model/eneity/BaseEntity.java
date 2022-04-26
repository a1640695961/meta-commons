package com.meta.commons.model.eneity;

import com.meta.commons.model.IOperation;

/**
 * @author Xiong Mao
 * @date 2022/04/26 19:51
 **/
public abstract class BaseEntity extends StringIdWithTime implements IOperation {

    private String createdBy;

    private String updatedBy;

    @Override
    public String getCreatedBy() {
        return createdBy;
    }

    @Override
    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    @Override
    public String getUpdatedBy() {
        return updatedBy;
    }

    @Override
    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }
}
