package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.ShareMoment;

/**
 * 评论表(ShareMoment)表服务接口
 */
public interface ShareMomentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ShareMoment queryById(Long id);


    /**
     * 新增数据
     *
     * @param shareMoment 实例对象
     * @return 实例对象
     */
    ShareMoment insert(ShareMoment shareMoment);

    /**
     * 修改数据
     *
     * @param shareMoment 实例对象
     * @return 实例对象
     */
    ShareMoment update(ShareMoment shareMoment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
