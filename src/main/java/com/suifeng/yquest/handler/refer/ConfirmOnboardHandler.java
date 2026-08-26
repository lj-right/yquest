package com.suifeng.yquest.handler.refer;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.api.enums.ReferApplyEventEnum;
import com.suifeng.yquest.api.enums.ReferApplyStatusEnum;
import com.suifeng.yquest.entity.Offer;
import com.suifeng.yquest.entity.OnboardRecord;
import com.suifeng.yquest.entity.ReferApply;
import com.suifeng.yquest.service.OfferService;
import com.suifeng.yquest.service.OnboardRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 确认入职事件处理器：同步更新Offer为已接受，并创建入职记录
 */
@Slf4j
@Service
public class ConfirmOnboardHandler implements ReferEventHandler {

    @Resource
    private OfferService offerService;

    @Resource
    private OnboardRecordService onboardRecordService;

    @Override
    public ReferApplyEventEnum getEvent() {
        return ReferApplyEventEnum.CONFIRM_ONBOARD;
    }

    @Override
    public void apply(ReferApply referApply, ReferApplyStatusEnum fromStatus) {
        Offer offer = offerService.getOne(Wrappers.<Offer>lambdaQuery()
                .eq(Offer::getReferApplyId, referApply.getId())
                .eq(Offer::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .last("limit 1"));
        if (offer == null) {
            throw new IllegalStateException("Offer不存在，无法确认入职！");
        }
        // 创建入职记录（已入职）
        OnboardRecord onboardRecord = new OnboardRecord();
        onboardRecord.setReferApplyId(referApply.getId());
        onboardRecord.setOfferId(offer.getId());
        onboardRecord.setJobSeekerId(referApply.getJobSeekerId());
        onboardRecord.setActualEntryDate(offer.getEntryDate() != null ? offer.getEntryDate() : new Date());
        onboardRecord.setStatus(1);
        onboardRecord.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        onboardRecordService.save(onboardRecord);
        if (log.isInfoEnabled()) {
            log.info("确认入职成功，创建入职记录，申请ID{}", referApply.getId());
        }
    }
}
