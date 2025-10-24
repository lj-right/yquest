package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.TrialCodeStatistics;

/**
 * (TrialCodeStatistics)表服务接口
 *
 */
public interface TrialCodeStatisticsService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TrialCodeStatistics queryById(Integer id);


    /**
     * 新增数据
     *
     * @param trialCodeStatistics 实例对象
     * @return 实例对象
     */
    TrialCodeStatistics insert(TrialCodeStatistics trialCodeStatistics);

    /**
     * 修改数据
     *
     * @param trialCodeStatistics 实例对象
     * @return 实例对象
     */
    boolean update(TrialCodeStatistics trialCodeStatistics);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
