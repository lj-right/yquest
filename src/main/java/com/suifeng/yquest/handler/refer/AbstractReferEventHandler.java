package com.suifeng.yquest.handler.refer;

import lombok.extern.slf4j.Slf4j;

/**
 * 内推状态机事件处理器抽象类（无副作用的纯状态转移事件直接继承即可）
 */
@Slf4j
public abstract class AbstractReferEventHandler implements ReferEventHandler {

    @Override
    public void apply(com.suifeng.yquest.entity.ReferApply referApply,
                      com.suifeng.yquest.api.enums.ReferApplyStatusEnum fromStatus) {
        // 默认无副作用
        if (log.isDebugEnabled()) {
            log.debug("事件{}无副作用处理", getEvent());
        }
    }
}
