package com.suifeng.yquest.service;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.entity.InterviewProcess;

import java.util.List;

/**
 * (InterviewProcess)表服务接口
 */
public interface InterviewProcessService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InterviewProcess queryById(Long id);

    /**
     * 分页查询
     *
     * @param interviewProcess 筛选条件
     * @return 查询结果
     */
    PageResult<InterviewProcess> queryByPage(InterviewProcess interviewProcess);

    /**
     * 条件查询
     *
     * @param interviewProcess 筛选条件
     * @return 结果列表
     */
    List<InterviewProcess> queryAll(InterviewProcess interviewProcess);

    /**
     * 新增数据
     *
     * @param interviewProcess 实例对象
     * @return 实例对象
     */
    boolean insert(InterviewProcess interviewProcess);

    /**
     * 修改数据
     *
     * @param interviewProcess 实例对象
     * @return 实例对象
     */
    boolean update(InterviewProcess interviewProcess);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据简历ID查询流程
     *
     * @param resumeId 简历ID
     * @return 流程
     */
    InterviewProcess queryByResumeId(Long resumeId);

    /**
     * 根据职位ID查询流程
     *
     * @param jobId 职位ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<InterviewProcess> queryByJobId(Long jobId, int pageNo, int pageSize);

    /**
     * 根据面试官ID查询流程
     *
     * @param interviewerId 面试官ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<InterviewProcess> queryByInterviewerId(Long interviewerId, int pageNo, int pageSize);

    /**
     * 推进面试流程
     *
     * @param id 流程ID
     * @param nextStage 下一阶段
     * @param nextStageTime 下一阶段时间
     * @param interviewerId 面试官ID
     * @param interviewerName 面试官姓名
     * @return 是否成功
     */
    boolean advanceProcess(Long id, Integer nextStage, java.util.Date nextStageTime, Long interviewerId, String interviewerName);

    /**
     * 结束面试流程
     *
     * @param id 流程ID
     * @param status 状态（1：已完成，2：已取消）
     * @return 是否成功
     */
    boolean endProcess(Long id, Integer status);

    /**
     * 拒绝面试流程
     *
     * @param id 流程ID
     * @return 是否成功
     */
    boolean rejectProcess(Long id);

    /**
     * 更新面试流程状态
     *
     * @param id 流程ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);

}
