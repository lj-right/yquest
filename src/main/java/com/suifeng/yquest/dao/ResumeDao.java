package com.suifeng.yquest.dao;

import com.suifeng.yquest.entity.Resume;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Resume)表数据库访问层
 */
@Mapper
public interface ResumeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Resume queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Resume> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 条件查询
     *
     * @param resume 查询条件
     * @return 对象列表
     */
    List<Resume> queryAll(@Param("resume") Resume resume);

    /**
     * 分页查询
     *
     * @param resume 查询条件
     * @param start 起始位置
     * @param pageSize 每页大小
     * @return 分页数据
     */
    List<Resume> queryPage(@Param("resume") Resume resume, @Param("start") int start, @Param("pageSize") Integer pageSize);

    /**
     * 计算总数
     *
     * @param resume 查询条件
     * @return 总数
     */
    int countByCondition(@Param("resume") Resume resume);

    /**
     * 新增数据
     *
     * @param resume 实例对象
     * @return 影响行数
     */
    int insert(Resume resume);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Resume> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Resume> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Resume> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Resume> entities);

    /**
     * 修改数据
     *
     * @param resume 实例对象
     * @return 影响行数
     */
    int update(Resume resume);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据职位ID查询简历
     *
     * @param jobId 职位ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 简历列表
     */
    List<Resume> queryByJobId(@Param("jobId") Long jobId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计职位的简历数量
     *
     * @param jobId 职位ID
     * @return 数量
     */
    int countByJobId(Long jobId);

    /**
     * 根据用户ID查询简历
     *
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 简历列表
     */
    List<Resume> queryByUserId(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计用户的简历数量
     *
     * @param userId 用户ID
     * @return 数量
     */
    int countByUserId(Long userId);

    /**
     * 根据职位ID和用户ID查询简历
     *
     * @param jobId 职位ID
     * @param userId 用户ID
     * @return 简历
     */
    Resume queryByJobIdAndUserId(@Param("jobId") Long jobId, @Param("userId") Long userId);

}
