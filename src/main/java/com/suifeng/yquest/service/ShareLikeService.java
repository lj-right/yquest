package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.ShareLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (ShareLike)表服务接口
 */
public interface ShareLikeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ShareLike queryById(Long id);

    /**
     * 新增数据
     *
     * @param shareLike 实例对象
     * @return 实例对象
     */
    Boolean insert(ShareLike shareLike);

    /**
     * 修改数据
     *
     * @param shareLike 实例对象
     * @return 实例对象
     */
    Boolean update(ShareLike shareLike);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    ShareLike getByMomentId(ShareLike shareLike);

    ShareLike getByCommentId(ShareLike shareLike);

    Boolean isLiked(ShareLike shareLike);

    Boolean add(ShareLike shareLike);

    Integer getAccount(ShareLike shareLike);
}
