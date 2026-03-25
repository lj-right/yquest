package com.suifeng.yquest.service;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.entity.Job;

import java.util.List;

/**
 * (Job)表服务接口
 */
public interface JobService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Job queryById(Long id);

    /**
     * 分页查询
     *
     * @param job 筛选条件
     * @return 查询结果
     */
    PageResult<Job> queryByPage(Job job);

    /**
     * 条件查询
     *
     * @param job 筛选条件
     * @return 结果列表
     */
    List<Job> queryAll(Job job);

    /**
     * 新增数据
     *
     * @param job 实例对象
     * @return 实例对象
     */
    boolean insert(Job job);

    /**
     * 修改数据
     *
     * @param job 实例对象
     * @return 实例对象
     */
    boolean update(Job job);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 增加浏览次数
     *
     * @param id 职位ID
     * @return 是否成功
     */
    boolean incrementViewCount(Long id);

    /**
     * 增加申请次数
     *
     * @param id 职位ID
     * @return 是否成功
     */
    boolean incrementApplyCount(Long id);

    /**
     * 查询用户发布的职位
     *
     * @return 分页结果
     */
    PageResult<Job> queryByUserId(Job job);

    /**
     * 审核职位
     *
     * @param id 职位ID
     * @param status 状态（1：通过，2：拒绝）
     * @return 是否成功
     */
    boolean auditJob(Long id, Integer status);

    /**
     * 结束职位
     *
     * @param id 职位ID
     * @return 是否成功
     */
    boolean closeJob(Long id);

    /**
     * 根据公司ID查询职位
     *
     * @return 分页结果
     */
    PageResult<Job> queryByCompanyId(Job job);

}
