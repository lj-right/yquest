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

    /**
     * 内推申请通知（通知管理员有新的内推申请待审核）
     *
     * @param fromId   申请人（求职者）loginId
     * @param toId     接收人（管理员）loginId
     * @param targetId 内推申请ID
     * @param msg      通知文案
     */
    void referApply(String fromId, String toId, Long targetId, String msg);

    Boolean unRead();

}
