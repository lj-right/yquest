package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.ShareCircle;

/**
 * 圈子表(ShareCircle)表服务接口
 */
public interface ShareCircleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ShareCircle queryById(Long id);


    /**
     * 新增数据
     *
     * @param shareCircle 实例对象
     * @return 实例对象
     */
    ShareCircle insert(ShareCircle shareCircle);

    /**
     * 修改数据
     *
     * @param shareCircle 实例对象
     * @return 实例对象
     */
    ShareCircle update(ShareCircle shareCircle);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
