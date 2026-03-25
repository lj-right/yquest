package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.Job;
import com.suifeng.yquest.dao.JobDao;
import com.suifeng.yquest.service.JobService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Job)表服务实现类
 */
@Service("jobService")
public class JobServiceImpl implements JobService {
    @Resource
    private JobDao jobDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Job queryById(Long id) {
        return this.jobDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param job 筛选条件
     * @return 查询结果
     */
    @Override
    public PageResult<Job> queryByPage(Job job) {
        PageResult<Job> pageResult = new PageResult<>();
        pageResult.setPageNo(job.getPageNo());
        pageResult.setPageSize(job.getPageSize());
        int start = (job.getPageNo() - 1) * job.getPageSize();

        int count = jobDao.countByCondition(job);
        if (count == 0) {
            return pageResult;
        }
        List<Job> jobList = jobDao.queryPage(job, start, job.getPageSize());
        pageResult.setRecords(jobList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 条件查询
     *
     * @param job 筛选条件
     * @return 结果列表
     */
    @Override
    public List<Job> queryAll(Job job) {
        return jobDao.queryAll(job);
    }

    /**
     * 新增数据
     *
     * @param job 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(Job job) {
        job.setStatus(0); // 待审核
        job.setViewCount(0);
        job.setApplyCount(0);
        job.setCreatedTime(new Date());
        job.setUpdatedTime(new Date());
        job.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return this.jobDao.insert(job) > 0;
    }

    /**
     * 修改数据
     *
     * @param job 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Job job) {
        job.setUpdatedTime(new Date());
        return this.jobDao.update(job) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.jobDao.deleteById(id) > 0;
    }

    /**
     * 增加浏览次数
     *
     * @param id 职位ID
     * @return 是否成功
     */
    @Override
    public boolean incrementViewCount(Long id) {
        return this.jobDao.incrementViewCount(id) > 0;
    }

    /**
     * 增加申请次数
     *
     * @param id 职位ID
     * @return 是否成功
     */
    @Override
    public boolean incrementApplyCount(Long id) {
        return this.jobDao.incrementApplyCount(id) > 0;
    }

    /**
     * 查询用户发布的职位
     *
     * @param userId 用户ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @Override
    public PageResult<Job> queryByUserId(Long userId, int pageNo, int pageSize) {
        PageResult<Job> pageResult = new PageResult<>();
        pageResult.setPageNo(pageNo);
        pageResult.setPageSize(pageSize);
        int start = (pageNo - 1) * pageSize;

        int count = jobDao.countByUserId(userId);
        if (count == 0) {
            return pageResult;
        }
        List<Job> jobList = jobDao.queryByUserId(userId, start, pageSize);
        pageResult.setRecords(jobList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 审核职位
     *
     * @param id 职位ID
     * @param status 状态（1：通过，2：拒绝）
     * @return 是否成功
     */
    @Override
    public boolean auditJob(Long id, Integer status) {
        Job job = new Job();
        job.setId(id);
        job.setStatus(status);
        job.setUpdatedTime(new Date());
        return this.jobDao.update(job) > 0;
    }

    /**
     * 结束职位
     *
     * @param id 职位ID
     * @return 是否成功
     */
    @Override
    public boolean closeJob(Long id) {
        Job job = new Job();
        job.setId(id);
        job.setStatus(2); // 已结束
        job.setUpdatedTime(new Date());
        return this.jobDao.update(job) > 0;
    }

}
