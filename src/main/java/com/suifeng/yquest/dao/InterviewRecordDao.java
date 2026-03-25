package com.suifeng.yquest.dao;

import com.suifeng.yquest.entity.InterviewRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (InterviewRecord)表数据库访问层
 */
@Mapper
public interface InterviewRecordDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    InterviewRecord queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<InterviewRecord> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 条件查询
     *
     * @param interviewRecord 查询条件
     * @return 对象列表
     */
    List<InterviewRecord> queryAll(InterviewRecord interviewRecord);

    /**
     * 分页查询
     *
     * @param interviewRecord 查询条件
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 分页数据
     */
    List<InterviewRecord> queryPage(InterviewRecord interviewRecord, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 计算总数
     *
     * @param interviewRecord 查询条件
     * @return 总数
     */
    int countByCondition(InterviewRecord interviewRecord);

    /**
     * 新增数据
     *
     * @param interviewRecord 实例对象
     * @return 影响行数
     */
    int insert(InterviewRecord interviewRecord);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<InterviewRecord> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<InterviewRecord> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<InterviewRecord> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<InterviewRecord> entities);

    /**
     * 修改数据
     *
     * @param interviewRecord 实例对象
     * @return 影响行数
     */
    int update(InterviewRecord interviewRecord);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

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
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 面试记录列表
     */
    List<InterviewRecord> queryByJobId(@Param("jobId") Long jobId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计职位的面试记录数量
     *
     * @param jobId 职位ID
     * @return 数量
     */
    int countByJobId(Long jobId);

    /**
     * 根据面试官ID查询面试记录
     *
     * @param interviewerId 面试官ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 面试记录列表
     */
    List<InterviewRecord> queryByInterviewerId(@Param("interviewerId") Long interviewerId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计面试官的面试记录数量
     *
     * @param interviewerId 面试官ID
     * @return 数量
     */
    int countByInterviewerId(Long interviewerId);

    /**
     * 根据流程ID和面试阶段查询面试记录
     *
     * @param processId 流程ID
     * @param stage 面试阶段
     * @return 面试记录
     */
    InterviewRecord queryByProcessIdAndStage(@Param("processId") Long processId, @Param("stage") Integer stage);

}
