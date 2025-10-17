package com.suifeng.yquest.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.entity.Refer;
import com.suifeng.yquest.entity.SensitiveWords;

/**
 *
 * 敏感词表 服务类
 */
public interface SensitiveWordsService extends IService<SensitiveWords> {

    PageResult<SensitiveWords> queryPage(SensitiveWords sw);
}
