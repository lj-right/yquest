package com.suifeng.yquest.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.yquest.api.req.RemoveShareCircleReq;
import com.suifeng.yquest.api.req.SaveShareCircleReq;
import com.suifeng.yquest.api.req.UpdateShareCircleReq;
import com.suifeng.yquest.api.vo.ShareCircleVO;
import com.suifeng.yquest.entity.ShareCircle;

import java.util.List;

/**
 * <p>
 * 圈子信息 服务类
 * </p>
 *
 * @author ChickenWing
 * @since 2024/05/16
 */
public interface ShareCircleService extends IService<ShareCircle> {

    List<ShareCircleVO> listResult();

    Boolean saveCircle(SaveShareCircleReq req);

    Boolean updateCircle(UpdateShareCircleReq req);

    Boolean removeCircle(RemoveShareCircleReq req);
}
