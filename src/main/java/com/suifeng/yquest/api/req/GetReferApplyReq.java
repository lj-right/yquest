package com.suifeng.yquest.api.req;

import com.suifeng.yquest.api.common.PageInfo;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 内推申请分页查询入参
 */
@Getter
@Setter
public class GetReferApplyReq implements Serializable {

    /**
     * 分页信息
     */
    private PageInfo pageInfo;

    /**
     * 当前状态过滤（可空）
     */
    private Integer currentStatus;
}
