package com.suifeng.yquest.handler.refer;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.api.enums.ReferApplyEventEnum;
import com.suifeng.yquest.api.enums.ReferApplyStatusEnum;
import com.suifeng.yquest.entity.OnboardRecord;
import com.suifeng.yquest.entity.ReferApply;
import com.suifeng.yquest.service.OnboardRecordService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * 试用期通过事件处理器：同步更新入职记录状态为试用期通过
 */
@Slf4j
@Service
public class ProbationPassHandler implements ReferEventHandler {

    @Resource
    private OnboardRecordService onboardRecordService;

    @Override
    public ReferApplyEventEnum getEvent() {
        return ReferApplyEventEnum.PROBATION_PASS;
    }

    @Override
    public void apply(ReferApply referApply, ReferApplyStatusEnum fromStatus) {
        OnboardRecord onboardRecord = onboardRecordService.getOne(Wrappers.<OnboardRecord>lambdaQuery()
                .eq(OnboardRecord::getReferApplyId, referApply.getId())
                .eq(OnboardRecord::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode())
                .last("limit 1"));
        if (onboardRecord == null) {
            throw new IllegalStateException("入职记录不存在，无法登记试用期结果！");
        }
        onboardRecord.setStatus(2);
        onboardRecord.setUpdatedTime(new Date());
        onboardRecordService.updateById(onboardRecord);
        if (log.isInfoEnabled()) {
            log.info("试用期通过登记成功，申请ID{}", referApply.getId());
        }
    }
}
