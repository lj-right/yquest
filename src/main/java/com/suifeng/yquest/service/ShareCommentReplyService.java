package com.suifeng.yquest.service;

import com.suifeng.yquest.entity.ShareCommentReply;

/**
 * 评论回复表(ShareCommentReply)表服务接口
 */
public interface ShareCommentReplyService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ShareCommentReply queryById(Long id);

    /**
     * 新增数据
     *
     * @param shareCommentReply 实例对象
     * @return 实例对象
     */
    ShareCommentReply insert(ShareCommentReply shareCommentReply);

    /**
     * 修改数据
     *
     * @param shareCommentReply 实例对象
     * @return 实例对象
     */
    ShareCommentReply update(ShareCommentReply shareCommentReply);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
