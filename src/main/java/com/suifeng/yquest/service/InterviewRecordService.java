package com.suifeng.yquest.service;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.entity.InterviewRecord;

import java.util.List;

/**
 * (InterviewRecord)表服务接口
 */
public interface InterviewRecordService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InterviewRecord queryById(Long id);

    /**
     * 分页查询
     *
     * @param interviewRecord 筛选条件
     * @return 查询结果
     */
    PageResult<InterviewRecord> queryByPage(InterviewRecord interviewRecord);

    /**
     * 条件查询
     *
     * @param interviewRecord 筛选条件
     * @return 结果列表
     */
    List<InterviewRecord> queryAll(InterviewRecord interviewRecord);

    /**
     * 新增数据
     *
     * @param interviewRecord 实例对象
     * @return 实例对象
     */
    boolean insert(InterviewRecord interviewRecord);

    /**
     * 修改数据
     *
     * @param interviewRecord 实例对象
     * @return 实例对象
     */
    boolean update(InterviewRecord interviewRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据流程ID查询面试记录
     *
     * @param processId 流程ID
     * @return 面试记录列表
     */
    List<InterviewRecord> queryByProcessId(Long processId);

    /**
     * 根据职位ID查询面试记录
     *
     * @param jobId 职位ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<InterviewRecord> queryByJobId(Long jobId, int pageNo, int pageSize);

    /**
     * 根据面试官ID查询面试记录
     *
     * @param interviewerId 面试官ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    PageResult<InterviewRecord> queryByInterviewerId(Long interviewerId, int pageNo, int pageSize);

    /**
     * 评估面试结果
     *
     * @param id 记录ID
     * @param result 结果（1：通过，2：未通过）
     * @param comments 评语
     * @return 操作结果
     */
    boolean evaluateInterview(Long id, Integer result, String comments);

}
