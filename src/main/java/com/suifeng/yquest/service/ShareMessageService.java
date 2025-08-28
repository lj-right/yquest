package com.suifeng.yquest.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.req.GetShareMessageReq;
import com.suifeng.yquest.api.vo.ShareMessageVO;
import com.suifeng.yquest.entity.ShareMessage;

/**
 *
 * 消息表 服务类
 */
public interface ShareMessageService extends IService<ShareMessage> {

    PageResult<ShareMessageVO> getMessages(GetShareMessageReq req);

    void comment(String fromId, String toId, Long targetId);

    void reply(String fromId, String toId, Long targetId);

    Boolean unRead();

}
