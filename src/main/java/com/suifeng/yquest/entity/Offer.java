package com.suifeng.yquest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Offer信息
 */
@Getter
@Setter
@TableName("offer")
public class Offer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * OfferID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内推申请ID
     */
    private Long referApplyId;

    /**
     * 职位ID
     */
    private Long jobId;

    /**
     * 求职者用户ID
     */
    private Long jobSeekerId;

    /**
     * Offer薪资
     */
    private BigDecimal salary;

    /**
     * 约定入职日期
     */
    private Date entryDate;

    /**
     * 接受截止时间
     */
    private Date deadline;

    /**
     * 状态（0：待接受，1：已接受，2：已拒绝，3：已过期）
     */
    private Integer status;

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
     * 是否删除（0：否，1：是）
     */
    private Integer isDeleted;
}
