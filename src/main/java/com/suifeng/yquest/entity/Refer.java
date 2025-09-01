package com.suifeng.yquest.entity;

import com.suifeng.yquest.api.common.PageInfo;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Refer)实体类
 */
@Data
public class Refer extends PageInfo implements Serializable {
    private static final long serialVersionUID = 593221499503852051L;
    /**
     * id
     */
    private Long id;
    /**
     * 公司名称
     */
    private String company;
    /**
     * 内推码
     */
    private String refercode;
    /**
     * 行业
     */
    private String industry;
    /**
     * 提交人
     */
    private String name;
    /**
     * 提交人的邮箱
     */
    private String email;
    /**
     * 热门排序
     */
    private Integer sort;
    /**
     * 描述
     */
    private String description;
    /**
     * 内推地址
     */
    private String url;
    /**
     * 创建人
     */
    private String createdBy;
    /**
     * 创建时间
     */
    private Date createdTime;
    /**
     * 更新人
     */
    private String updatedBy;
    /**
     * 更新时间
     */
    private Date updatedTime;
    /**
     * 是否删除0否1是
     */
    private Integer isDeleted;
}

