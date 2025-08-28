package com.suifeng.yquest.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.req.GetShareMomentReq;
import com.suifeng.yquest.api.req.RemoveShareMomentReq;
import com.suifeng.yquest.api.req.SaveMomentCircleReq;
import com.suifeng.yquest.api.vo.ShareMomentVO;
import com.suifeng.yquest.entity.ShareMoment;

/**
 *
 * 动态信息 服务类
 */
public interface ShareMomentService extends IService<ShareMoment> {

    Boolean saveMoment(SaveMomentCircleReq req);

    PageResult<ShareMomentVO> getMoments(GetShareMomentReq req);

    Boolean removeMoment(RemoveShareMomentReq req);

    void incrReplyCount(Long id, int count);

}
