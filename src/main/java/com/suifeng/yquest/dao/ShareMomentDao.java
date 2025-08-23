package com.suifeng.yquest.dao;

import com.suifeng.yquest.entity.ShareMoment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * 评论2表(ShareMoment)表数据库访问层
 */
@Mapper
public interface ShareMomentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ShareMoment queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param shareMoment 查询条件
     * @param pageable    分页对象
     * @return 对象列表
     */
    List<ShareMoment> queryAllByLimit(ShareMoment shareMoment, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param shareMoment 查询条件
     * @return 总行数
     */
    long count(ShareMoment shareMoment);

    /**
     * 新增数据
     *
     * @param shareMoment 实例对象
     * @return 影响行数
     */
    int insert(ShareMoment shareMoment);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ShareMoment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ShareMoment> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ShareMoment> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ShareMoment> entities);

    /**
     * 修改数据
     *
     * @param shareMoment 实例对象
     * @return 影响行数
     */
    int update(ShareMoment shareMoment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

