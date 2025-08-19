package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.SensitiveWords;

/**
 * 敏感词表(SensitiveWords)表服务接口
 */
public interface SensitiveWordsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    SensitiveWords queryById(Long id);


    /**
     * 新增数据
     *
     * @param sensitiveWords 实例对象
     * @return 实例对象
     */
    SensitiveWords insert(SensitiveWords sensitiveWords);

    /**
     * 修改数据
     *
     * @param sensitiveWords 实例对象
     * @return 实例对象
     */
    SensitiveWords update(SensitiveWords sensitiveWords);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
