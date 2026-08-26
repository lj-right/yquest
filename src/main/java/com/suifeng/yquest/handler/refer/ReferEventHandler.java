package com.suifeng.yquest.handler.refer;

import com.suifeng.yquest.api.enums.ReferApplyEventEnum;
import com.suifeng.yquest.api.enums.ReferApplyStatusEnum;
import com.suifeng.yquest.entity.ReferApply;

/**
 * 内推状态机事件处理器
 */
public interface ReferEventHandler {

    /**
     * 获取处理的事件
     *
     * @return 事件枚举
     */
    ReferApplyEventEnum getEvent();

    /**
     * 执行事件副作用（在状态更新前调用，与状态更新同事务）
     * 例如：终试通过时自动创建Offer、确认入职时创建入职记录等
     *
     * @param referApply 内推申请
     * @param fromStatus 流转前状态
     */
    void apply(ReferApply referApply, ReferApplyStatusEnum fromStatus);
}
