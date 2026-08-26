package com.suifeng.yquest.handler.refer;

import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.api.enums.ReferApplyEventEnum;
import com.suifeng.yquest.api.enums.ReferApplyStatusEnum;
import com.suifeng.yquest.entity.Offer;
import com.suifeng.yquest.entity.ReferApply;
import com.suifeng.yquest.service.OfferService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 面试通过事件处理器：终试通过时自动创建Offer记录（待接受）
 */
@Slf4j
@Service
public class InterviewPassHandler implements ReferEventHandler {

    @Resource
    private OfferService offerService;

    @Override
    public ReferApplyEventEnum getEvent() {
        return ReferApplyEventEnum.INTERVIEW_PASS;
    }

    @Override
    public void apply(ReferApply referApply, ReferApplyStatusEnum fromStatus) {
        if (fromStatus != ReferApplyStatusEnum.FINAL_INTERVIEW) {
            // 初试/复试通过无副作用
            return;
        }
        // 终试通过：自动创建Offer（待接受）
        Offer offer = new Offer();
        offer.setReferApplyId(referApply.getId());
        offer.setJobId(referApply.getJobId());
        offer.setJobSeekerId(referApply.getJobSeekerId());
        offer.setStatus(0);
        offer.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        offerService.save(offer);
        if (log.isInfoEnabled()) {
            log.info("终试通过，自动创建Offer，申请ID{}", referApply.getId());
        }
    }
}
