package com.suifeng.yquest.service;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.entity.Refer;

/**
 * (Refer)表服务接口
 */
public interface ReferService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Refer queryById(Long id);


    /**
     * 新增数据
     *
     * @param refer 实例对象
     * @return 实例对象
     */
    Refer insert(Refer refer);

    /**
     * 修改数据
     *
     * @param refer 实例对象
     * @return 实例对象
     */
    Integer update(Refer refer);

    /**
     * 通过主键删除数据
     *
     * @param refer 实例对象
     * @return 实例对象
     */
    Integer deleteById(Refer refer);

    PageResult<Refer> queryReferPage(Refer refer);

    PageResult<Refer> manageReferPage(Refer refer);
}
