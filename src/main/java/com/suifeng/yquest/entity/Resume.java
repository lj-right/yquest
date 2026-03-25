package com.suifeng.yquest.entity;

import com.suifeng.yquest.api.common.PageInfo;
import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (Resume)实体类
 */
@Data
public class Resume extends PageInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 简历ID
     */
    private Long id;

    /**
     * 职位ID
     */
    private Long jobId;

    /**
     * 求职者ID
     */
    private Long userId;

    /**
     * 求职者姓名
     */
    private String userName;

    /**
     * 求职者邮箱
     */
    private String userEmail;

    /**
     * 求职者电话
     */
    private String userPhone;

    /**
     * 简历文件URL
     */
    private String resumeFileUrl;

    /**
     * 简历文件名
     */
    private String resumeFileName;

    /**
     * 自我介绍
     */
    private String selfIntroduction;

    /**
     * 状态（0：待处理，1：已通过，2：已拒绝）
     */
    private Integer status;

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
