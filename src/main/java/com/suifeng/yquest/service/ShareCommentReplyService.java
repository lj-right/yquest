package com.suifeng.yquest.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.suifeng.yquest.api.req.GetShareCommentReq;
import com.suifeng.yquest.api.req.RemoveShareCommentReq;
import com.suifeng.yquest.api.req.SaveShareCommentReplyReq;
import com.suifeng.yquest.api.vo.ShareCommentReplyVO;
import com.suifeng.yquest.entity.ShareCommentReply;
import java.util.List;

/**
 * <p>
 * 评论及回复信息 服务类
 * </p>
 *
 * @author ChickenWing
 * @since 2024/05/16
 */
public interface ShareCommentReplyService extends IService<ShareCommentReply> {

    Boolean saveComment(SaveShareCommentReplyReq req);

    Boolean removeComment(RemoveShareCommentReq req);

    List<ShareCommentReplyVO> listComment(GetShareCommentReq req);

}
