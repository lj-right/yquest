package com.suifeng.yquest.dao;

import com.suifeng.yquest.entity.Refer;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * (Refer)表数据库访问层
 */
@Mapper
public interface ReferDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Refer queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param refer    查询条件
     * @param pageable 分页对象
     * @return 对象列表
     */
    List<Refer> queryAllByLimit(Refer refer, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param refer 查询条件
     * @return 总行数
     */
    long count(Refer refer);

    /**
     * 新增数据
     *
     * @param refer 实例对象
     * @return 影响行数
     */
    int insert(Refer refer);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Refer> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Refer> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Refer> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<Refer> entities);

    /**
     * 修改数据
     *
     * @param refer 实例对象
     * @return 影响行数
     */
    int update(Refer refer);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    int countByCondition(Refer refer);

    List<Refer> queryAllRefer(@Param("start") int start, @Param("pageSize") Integer pageSize);

    int countByDelCondition(Refer refer);

    List<Refer> queryAllDelRefer(@Param("start") int start, @Param("pageSize") Integer pageSize);
}

