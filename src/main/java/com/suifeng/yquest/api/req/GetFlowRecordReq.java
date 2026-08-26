package com.suifeng.yquest.api.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 内推流转记录查询入参
 */
@Getter
@Setter
public class GetFlowRecordReq implements Serializable {

    /**
     * 内推申请ID
     */
    private Long applyId;
}
