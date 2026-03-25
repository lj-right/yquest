package com.suifeng.yquest.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.suifeng.yquest.api.common.PageInfo;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 公司表(Company)实体类
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Company extends PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 公司ID
     */
    private Long id;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 公司规模
     */
    private String companySize;

    /**
     * 公司简介
     */
    private String companyDesc;

    /**
     * 公司地址
     */
    private String companyAddress;

    /**
     * 联系电话
     */
    private String contactPhone;

    /**
     * 联系人
     */
    private String contactPerson;

    /**
     * 审核状态（0：待审核，1：已通过，2：已拒绝）
     */
    private Integer auditStatus;

    /**
     * 创建时间
     */
    private Date createdTime;

    /**
     * 更新时间
     */
    private Date updatedTime;

    /**
     * 是否删除（0：否，1：是）
     */
    private Integer isDeleted;

    // 构造方法、getter和setter由lombok自动生成
}
