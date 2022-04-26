package com.meta.commons.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

/**
 * @author Xiong Mao
 * @date 2022/04/26 19:53
 **/
@Data
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class PageModel<T> {
    /**
     * 当前页码
     */
    private Long pageNo;
    /**
     * 每页数量
     */
    private Long pageSize;
    /**
     * 总页数
     */
    private Long totalPage;
    /**
     * 总数量
     */
    private Long totalCount;
    private List<T> list;

    public PageModel(long pageNo, long pageSize, long totalPage, long totalCount, List<T> records) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
        this.totalPage = totalPage;
        this.totalCount = totalCount;
        this.list = records;
    }

}
