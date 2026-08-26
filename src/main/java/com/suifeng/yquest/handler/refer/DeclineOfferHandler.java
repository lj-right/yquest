package com.suifeng.yquest.handler.refer;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.api.enums.ReferApplyEventEnum;
import com.suifeng.yquest.api.enums.ReferApplyStatusEnum;
import com.suifeng.yquest.entity.Offer;
import com.suifeng.yquest.entity.ReferApply;
import com.suifeng.yquest.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 拒绝Offer事件处理器：同步更新Offer状态为已拒绝
 */
@Slf4j
@Service
public class DeclineOfferHandler implements ReferEventHandler {

    @Resource
    private OfferService offerService;

    @Override
    public ReferApplyEventEnum getEvent() {
        return ReferApplyEventEnum.DECLINE_OFFER;
    }

    @Override
    public void apply(ReferApply referApply, ReferApplyStatusEnum fromStatus) {
        Offer offer = offerService.getOne(Wrappers.<Offer>lambdaQuery()
                .eq(Offer::getReferApplyId, referApply.getId())
                .eq(Offer::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .last("limit 1"));
        if (offer == null) {
            throw new IllegalStateException("Offer不存在，无法拒绝！");
        }
        offer.setStatus(2);
        offer.setUpdatedTime(new Date());
        offerService.updateById(offer);
        if (log.isInfoEnabled()) {
            log.info("拒绝Offer成功，申请ID{}，OfferID{}", referApply.getId(), offer.getId());
        }
    }
}
