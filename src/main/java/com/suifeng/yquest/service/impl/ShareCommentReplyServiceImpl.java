package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.entity.ShareCommentReply;
import com.suifeng.yquest.dao.ShareCommentReplyDao;
import com.suifeng.yquest.service.ShareCommentReplyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 评论回复表(ShareCommentReply)表服务实现类
 */
@Service("shareCommentReplyService")
public class ShareCommentReplyServiceImpl implements ShareCommentReplyService {
    @Resource
    private ShareCommentReplyDao shareCommentReplyDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ShareCommentReply queryById(Long id) {
        return this.shareCommentReplyDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param shareCommentReply 实例对象
     * @return 实例对象
     */
    @Override
    public ShareCommentReply insert(ShareCommentReply shareCommentReply) {
        this.shareCommentReplyDao.insert(shareCommentReply);
        return shareCommentReply;
    }

    /**
     * 修改数据
     *
     * @param shareCommentReply 实例对象
     * @return 实例对象
     */
    @Override
    public ShareCommentReply update(ShareCommentReply shareCommentReply) {
        this.shareCommentReplyDao.update(shareCommentReply);
        return this.queryById(shareCommentReply.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.shareCommentReplyDao.deleteById(id) > 0;
    }
}
