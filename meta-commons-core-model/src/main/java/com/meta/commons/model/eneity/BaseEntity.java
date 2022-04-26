package com.meta.commons.model.eneity;

import com.meta.commons.model.IOperation;
import com.meta.commons.model.ITimeEntity;

import java.util.Date;

/**
 * @author Xiong Mao
 * @date 2022/04/26 19:51
 **/
public class BaseEntity extends StringId implements IOperation, ITimeEntity {

    protected Date createdAt;

    protected Date updatedAt;

    protected String createdBy;

    protected String updatedBy;

    protected String remark;

    protected boolean isDelete;

    public static BaseEntity deleteMode(String id) {
        BaseEntity baseEntity = new BaseEntity();
        baseEntity.setId(id);
        baseEntity.setDelete(true);
        return baseEntity;
    }

    public boolean isDelete() {
        return isDelete;
    }

    public void setDelete(boolean delete) {
        isDelete = delete;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

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
