package com.suifeng.yquest.entity;

import com.suifeng.yquest.api.common.PageInfo;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (TrialCode)实体类
 */
@Data
public class TrialCode extends PageInfo implements Serializable {
    private static final long serialVersionUID = 768615587052233875L;

    private Integer id;

    private String code;

    private Integer status;

    private Date expireTime;

    private String createdBy;

    private Date createdTime;

    private String updatedBy;

    private Date updatedTime;

    private Integer isDeleted;
}

