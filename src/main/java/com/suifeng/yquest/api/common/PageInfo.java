package com.suifeng.yquest.api.common;

import lombok.Data;

import java.io.Serializable;

/**
 * 分页请求实体
 */
@Data
public class PageInfo implements Serializable {

    private Integer pageNo = 1;

    private Integer pageSize = 10;

    public Integer getPageNo() {
        return (pageNo < 1 || pageNo == null) ? 1 : pageNo;
    }

    public Integer getPageSize() {
        return (pageSize < 1 || pageSize == null || pageSize > Integer.MAX_VALUE) ? 10 : pageSize;
    }

}
