package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.ShareMessage;

/**
 * 信息提醒表(ShareMessage)表服务接口
 */
public interface ShareMessageService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ShareMessage queryById(Long id);


    /**
     * 新增数据
     *
     * @param shareMessage 实例对象
     * @return 实例对象
     */
    ShareMessage insert(ShareMessage shareMessage);

    /**
     * 修改数据
     *
     * @param shareMessage 实例对象
     * @return 实例对象
     */
    ShareMessage update(ShareMessage shareMessage);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
