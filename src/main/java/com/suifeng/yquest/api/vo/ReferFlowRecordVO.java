package com.suifeng.yquest.api.vo;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 内推流转记录信息
 */
@Getter
@Setter
public class ReferFlowRecordVO implements Serializable {

    /**
     * 记录ID
     */
    private Long id;

    /**
     * 流转前状态码
     */
    private Integer fromStatus;

    /**
     * 流转前状态描述
     */
    private String fromStatusDesc;

    /**
     * 流转后状态码
     */
    private Integer toStatus;

    /**
     * 流转后状态描述
     */
    private String toStatusDesc;

    /**
     * 触发事件
     */
    private String event;

    /**
     * 操作人姓名
     */
    private String operatorName;

    /**
     * 操作时角色
     */
    private String operatorRole;

    /**
     * 备注
     */
    private String remark;

    /**
     * 操作时间（毫秒时间戳）
     */
    private Long createdTime;
}
