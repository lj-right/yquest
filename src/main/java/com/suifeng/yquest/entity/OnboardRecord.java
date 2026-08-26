package com.suifeng.yquest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 入职记录信息
 */
@Getter
@Setter
@TableName("onboard_record")
public class OnboardRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 入职记录ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内推申请ID
     */
    private Long referApplyId;

    /**
     * OfferID
     */
    private Long offerId;

    /**
     * 求职者用户ID
     */
    private Long jobSeekerId;

    /**
     * 实际入职日期
     */
    private Date actualEntryDate;

    /**
     * 试用期截止日期
     */
    private Date probationEndDate;

    /**
     * 状态（0：待入职，1：已入职，2：试用期通过，3：试用期未通过，4：放弃入职）
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
