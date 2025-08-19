package com.suifeng.yquest.entity;

import lombok.Data;

import java.util.Date;
import java.io.Serializable;

/**
 * 圈子表(ShareCircle)实体类
 */
@Data
public class ShareCircle implements Serializable {
    private static final long serialVersionUID = 837529459427516150L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 父类id
     */
    private Long parentId;
    /**
     * 交流圈名称
     */
    private String circleName;
    /**
     * 图片
     */
    private String icon;
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
    private String updateBy;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 是否删除
     */
    private Integer isDeleted;
}

