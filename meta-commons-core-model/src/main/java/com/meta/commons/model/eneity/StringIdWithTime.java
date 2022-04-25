package com.meta.commons.model.eneity;

import com.meta.commons.model.ITimeEntity;

import java.util.Date;

/**
 * @author Xiong Mao
 * @date 2022/04/24 23:36
 **/
public abstract class StringIdWithTime extends StringId implements ITimeEntity {

    protected Date createdAt;

    protected Date updatedAt;

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }

    @Override
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public Date getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
