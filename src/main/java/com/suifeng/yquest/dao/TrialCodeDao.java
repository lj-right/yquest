package com.suifeng.yquest.dao;

import com.suifeng.yquest.entity.TrialCode;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Set;

/**
 * (TrialCode)表数据库访问层
 */
public interface TrialCodeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TrialCode queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param trialCode 查询条件
     * @param pageable  分页对象
     * @return 对象列表
     */
    List<TrialCode> queryAllByLimit(TrialCode trialCode, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param trialCode 查询条件
     * @return 总行数
     */
    long count(TrialCode trialCode);

    /**
     * 新增数据
     *
     * @param trialCode 实例对象
     * @return 影响行数
     */
    int insert(TrialCode trialCode);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<TrialCode> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") Set<TrialCode> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<TrialCode> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<TrialCode> entities);

    /**
     * 修改数据
     *
     * @param trialCode 实例对象
     * @return 影响行数
     */
    int update(@Param("list") Set<TrialCode> trialCode);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

    int countByCondition(TrialCode trialCode);

    List<TrialCode> queryAllCode(@Param("start") int start,@Param("pageSize") Integer pageSize);

    Set<TrialCode> queryExpiredCodes( TrialCode trialCode);

    int updateByCode(TrialCode trialCode);
}

