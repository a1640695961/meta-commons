package com.meta.commons.model;

import java.util.Date;

/**
 * @author Xiong Mao
 * @date 2022/04/24 23:33
 **/
public interface ITimeEntity {
    Date getCreatedAt();

    void setCreatedAt(Date createdAt);

    Date getUpdatedAt();

    void setUpdatedAt(Date updatedAt);

}
