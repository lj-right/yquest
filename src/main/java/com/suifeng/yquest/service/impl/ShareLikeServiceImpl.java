package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.api.enums.LikeTypeEnum;
import com.suifeng.yquest.entity.ShareCommentReply;
import com.suifeng.yquest.entity.ShareLike;
import com.suifeng.yquest.dao.ShareLikeDao;
import com.suifeng.yquest.entity.ShareMoment;
import com.suifeng.yquest.handler.LikeByComment;
import com.suifeng.yquest.handler.LikeByMoment;
import com.suifeng.yquest.service.ShareLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;

import static com.suifeng.yquest.api.enums.LikeTypeEnum.*;

/**
 * (ShareLike)表服务实现类
 */
@Service("shareLikeService")
public class ShareLikeServiceImpl implements ShareLikeService {
    @Resource
    private ShareLikeDao shareLikeDao;

    @Resource
    private LikeByComment likeByComment;

    @Resource
    private LikeByMoment likeByMoment;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ShareLike queryById(Long id) {
        return this.shareLikeDao.queryById(id);
    }

    /**
     * 通过circleId查询单条数据
     */
    @Override
    public ShareLike getByMomentId(ShareLike shareLike) {
        return shareLikeDao.queryByMomentId(shareLike.getMomentId());
    }

    /**
     * 通过commentId查询单条数据
     */
    @Override
    public ShareLike getByCommentId(ShareLike shareLike) {
        return shareLikeDao.queryByCommentId(shareLike.getCommentId());
    }

    /**
     * 查询评论是否点赞
     */
    @Override
    public Boolean isLiked(ShareLike shareLike) {
        switch (LikeTypeEnum.getByCode(shareLike.getType())) {
            case MOMENT:
                return likeByMoment.isLiked(shareLike.getMomentId());
            case COMMENT:
                return likeByComment.isLiked(shareLike.getCommentId());
            default:
                return false;
        }
    }

    /**
     * 新增数据(持久化)
     */
    @Override
    public Boolean add(ShareLike shareLike){
        return shareLikeDao.insert(shareLike) > 0;
    }

    /**
     * 新增数据(无持久化)
     *
     * @param shareLike 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(ShareLike shareLike) {
        Long momentId = shareLike.getMomentId();
        Long commentId = shareLike.getCommentId();
        if (momentId != null && momentId > 0) {
            ShareMoment shareMoment = new ShareMoment();
            shareMoment.setId(momentId);
            shareMoment.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            return likeByMoment.like(shareMoment);
        } else if (commentId != null && commentId >0) {
            ShareCommentReply shareCommentReply = new ShareCommentReply();
            shareCommentReply.setId(commentId);
            shareCommentReply.setReplyType(shareLike.getType());
            shareCommentReply.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            return likeByComment.like(shareCommentReply);
        }
        return false;
    }

    /**
     * 修改数据
     *
     * @param shareLike 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean update(ShareLike shareLike) {

        return this.shareLikeDao.update(shareLike) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.shareLikeDao.deleteById(id) > 0;
    }


}
