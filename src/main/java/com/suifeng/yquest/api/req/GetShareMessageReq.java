package com.suifeng.yquest.api.req;


import com.suifeng.yquest.api.common.PageInfo;
import lombok.Data;

import java.io.Serializable;

@Data
public class GetShareMessageReq implements Serializable {

    /**
     * 是否被阅读 1是 2否
     */
    private Integer isRead;

    /**
     * 分页信息
     */
    private PageInfo pageInfo;

}