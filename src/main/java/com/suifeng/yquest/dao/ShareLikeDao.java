package com.suifeng.yquest.dao;

import com.suifeng.yquest.entity.ShareLike;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (ShareLike)表数据库访问层
 */
public interface ShareLikeDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ShareLike queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param shareLike 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ShareLike> queryAllByLimit(ShareLike shareLike, @Param("pageable") Pageable pageable);

    /**
     * 统计总行数
     *
     * @param shareLike 查询条件
     * @return 总行数
     */
    long count(ShareLike shareLike);

    /**
     * 新增数据
     *
     * @param shareLike 实例对象
     * @return 影响行数
     */
    int insert(ShareLike shareLike);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ShareLike> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ShareLike> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ShareLike> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ShareLike> entities);

    /**
     * 修改数据
     *
     * @param shareLike 实例对象
     * @return 影响行数
     */
    int update(ShareLike shareLike);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    ShareLike queryByCommentId(Long commentId);


    ShareLike queryByMomentId(Long momentId);
}

