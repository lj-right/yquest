package com.suifeng.yquest.service;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.entity.Resume;

import java.util.List;

/**
 * (Resume)表服务接口
 */
public interface ResumeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Resume queryById(Long id);

    /**
     * 分页查询
     *
     * @param resume 筛选条件
     * @return 查询结果
     */
    PageResult<Resume> queryByPage(Resume resume);

    /**
     * 条件查询
     *
     * @param resume 筛选条件
     * @return 结果列表
     */
    List<Resume> queryAll(Resume resume);

    /**
     * 新增数据
     *
     * @param resume 实例对象
     * @return 实例对象
     */
    boolean insert(Resume resume);

    /**
     * 修改数据
     *
     * @param resume 实例对象
     * @return 实例对象
     */
    boolean update(Resume resume);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据职位ID查询简历
     *
     */
    PageResult<Resume> queryByJobId(Resume resume);

    /**
     * 根据用户ID查询简历
     * @return 分页结果
     */
    PageResult<Resume> queryByUserId(Resume resume);

    /**
     * 根据职位ID和用户ID查询简历
     *
     * @param jobId 职位ID
     * @param userId 用户ID
     * @return 简历
     */
    Resume queryByJobIdAndUserId(Long jobId, Long userId);

    /**
     * 处理简历状态
     *
     * @param id 简历ID
     * @param status 状态（1：通过，2：拒绝）
     * @return 是否成功
     */
    boolean processResume(Long id, Integer status);

}
