package com.meta.commons.model.eneity;

import com.meta.commons.model.IEntity;

/**
 * @author Xiong Mao
 * @date 2022/04/24 23:35
 **/
public abstract class StringId implements IEntity<String> {

    protected String id;

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }
}
