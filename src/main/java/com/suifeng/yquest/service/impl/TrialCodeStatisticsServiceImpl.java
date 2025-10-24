package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.dao.TrialCodeStatisticsDao;
import com.suifeng.yquest.entity.TrialCodeStatistics;
import com.suifeng.yquest.service.TrialCodeStatisticsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * (TrialCodeStatistics)表服务实现类
 */
@Service("trialCodeStatisticsService")
public class TrialCodeStatisticsServiceImpl implements TrialCodeStatisticsService {

    @Resource
    private TrialCodeStatisticsDao trialCodeStatisticsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TrialCodeStatistics queryById(Integer id) {
        return this.trialCodeStatisticsDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param trialCodeStatistics 实例对象
     * @return 实例对象
     */
    @Override
    public TrialCodeStatistics insert(TrialCodeStatistics trialCodeStatistics) {
        trialCodeStatistics.setCreatedTime(new Date());
        trialCodeStatistics.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        this.trialCodeStatisticsDao.insert(trialCodeStatistics);
        return trialCodeStatistics;
    }

    /**
     * 修改数据
     *
     * @param trialCodeStatistics 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(TrialCodeStatistics trialCodeStatistics) {
        return this.trialCodeStatisticsDao.update(trialCodeStatistics) > 0;
    }


    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.trialCodeStatisticsDao.deleteById(id) > 0;
    }
}
