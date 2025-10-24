package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * (TrialCodeStatistics)实体类
 */
@Data
public class TrialCodeStatistics implements Serializable {
    private static final long serialVersionUID = -90699485695774656L;
    /**
     * 主键
     */
    private Integer id;
    /**
     * 可用数量
     */
    private Long availableCount;
    /**
     * 过期数量
     */
    private Long expiredCount;
    /**
     * 总数量
     */
    private Long totalGenerated;
    /**
     * 使用率
     */
    private Double usePercentage;
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

