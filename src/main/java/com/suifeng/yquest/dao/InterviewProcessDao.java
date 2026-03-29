package com.suifeng.yquest.dao;

import com.suifeng.yquest.entity.InterviewProcess;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (InterviewProcess)表数据库访问层
 */
@Mapper
public interface InterviewProcessDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InterviewProcess queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<InterviewProcess> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 条件查询
     *
     * @param interviewProcess 查询条件
     * @return 对象列表
     */
    List<InterviewProcess> queryAll(@Param("interviewProcess") InterviewProcess interviewProcess);

    /**
     * 分页查询
     *
     * @param interviewProcess 查询条件
     * @param start 起始位置
     * @param pageSize 每页大小
     * @return 分页数据
     */
    List<InterviewProcess> queryPage(@Param("interviewProcess") InterviewProcess interviewProcess, @Param("start") int start, @Param("pageSize") Integer pageSize);

    /**
     * 计算总数
     *
     * @param interviewProcess 查询条件
     * @return 总数
     */
    int countByCondition(@Param("interviewProcess") InterviewProcess interviewProcess);

    /**
     * 新增数据
     *
     * @param interviewProcess 实例对象
     * @return 影响行数
     */
    int insert(InterviewProcess interviewProcess);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<InterviewProcess> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<InterviewProcess> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<InterviewProcess> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<InterviewProcess> entities);

    /**
     * 修改数据
     *
     * @param interviewProcess 实例对象
     * @return 影响行数
     */
    int update(InterviewProcess interviewProcess);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

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
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 流程列表
     */
    List<InterviewProcess> queryByJobId(@Param("jobId") Long jobId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计职位的流程数量
     *
     * @param jobId 职位ID
     * @return 数量
     */
    int countByJobId(Long jobId);

    /**
     * 根据面试官ID查询流程
     *
     * @param interviewerId 面试官ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 流程列表
     */
    List<InterviewProcess> queryByInterviewerId(@Param("interviewerId") Long interviewerId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计面试官的流程数量
     *
     * @param interviewerId 面试官ID
     * @return 数量
     */
    int countByInterviewerId(Long interviewerId);

}
