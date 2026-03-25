package com.suifeng.yquest.entity;

import com.suifeng.yquest.api.common.PageInfo;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (Job)实体类
 */
@Data
public class Job extends PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 职位ID
     */
    private Long id;

    /**
     * 公司ID
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 职位名称
     */
    private String jobTitle;

    /**
     * 部门
     */
    private String department;

    /**
     * 工作地点
     */
    private String location;

    /**
     * 最低薪资
     */
    private BigDecimal salaryMin;

    /**
     * 最高薪资
     */
    private BigDecimal salaryMax;

    /**
     * 工作经验要求
     */
    private String experience;

    /**
     * 学历要求
     */
    private String education;

    /**
     * 职位类型（全职/兼职/实习）
     */
    private String jobType;

    /**
     * 职位描述
     */
    private String description;

    /**
     * 职位要求
     */
    private String requirements;

    /**
     * 发布人ID
     */
    private Long publishUserId;

    /**
     * 发布人姓名
     */
    private String publishUserName;

    /**
     * 发布人邮箱
     */
    private String publishUserEmail;

    /**
     * 状态（0：待审核，1：已发布，2：已结束）
     */
    private Integer status;

    /**
     * 浏览次数
     */
    private Integer viewCount;

    /**
     * 申请次数
     */
    private Integer applyCount;

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
