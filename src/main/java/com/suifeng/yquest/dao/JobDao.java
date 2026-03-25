package com.suifeng.yquest.dao;

import com.suifeng.yquest.entity.Job;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (Job)表数据库访问层
 */
@Mapper
public interface JobDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Job queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Job> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 条件查询
     *
     * @param job 查询条件
     * @return 对象列表
     */
    List<Job> queryAll(@Param("job") Job job);

    /**
     * 分页查询
     *
     * @param job 查询条件
     * @param start 起始位置
     * @param pageSize 每页大小
     * @return 分页数据
     */
    List<Job> queryPage(@Param("job") Job job, @Param("start") int start, @Param("pageSize") Integer pageSize);

    /**
     * 计算总数
     *
     * @param job 查询条件
     * @return 总数
     */
    int countByCondition(@Param("job") Job job);

    /**
     * 新增数据
     *
     * @param job 实例对象
     * @return 影响行数
     */
    int insert(Job job);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Job> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Job> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Job> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Job> entities);

    /**
     * 修改数据
     *
     * @param job 实例对象
     * @return 影响行数
     */
    int update(Job job);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 增加浏览次数
     *
     * @param id 职位ID
     * @return 影响行数
     */
    int incrementViewCount(Long id);

    /**
     * 增加申请次数
     *
     * @param id 职位ID
     * @return 影响行数
     */
    int incrementApplyCount(Long id);

    /**
     * 查询用户发布的职位
     *
     * @param userId 用户ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 职位列表
     */
    List<Job> queryByUserId(@Param("userId") Long userId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计用户发布的职位数量
     *
     * @param userId 用户ID
     * @return 数量
     */
    int countByUserId(Long userId);

    /**
     * 查询公司发布的职位
     *
     * @param companyId 公司ID
     * @param offset 偏移量
     * @param limit 限制数量
     * @return 职位列表
     */
    List<Job> queryByCompanyId(@Param("companyId") Long companyId, @Param("offset") int offset, @Param("limit") int limit);

    /**
     * 统计公司发布的职位数量
     *
     * @param companyId 公司ID
     * @return 数量
     */
    int countByCompanyId(Long companyId);

}
