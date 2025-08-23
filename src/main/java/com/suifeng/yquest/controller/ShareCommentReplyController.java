package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.ShareCommentReply;
import com.suifeng.yquest.service.ShareCommentReplyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论回复表(ShareCommentReply)表控制层
 */
@RestController
@RequestMapping("/shareCommentReply")
public class ShareCommentReplyController {
    /**
     * 服务对象
     */
    @Resource
    private ShareCommentReplyService shareCommentReplyService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ShareCommentReply> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.shareCommentReplyService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param shareCommentReply 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseEntity<ShareCommentReply> add(@RequestBody ShareCommentReply shareCommentReply) {
        return ResponseEntity.ok(this.shareCommentReplyService.insert(shareCommentReply));
    }

    /**
     * 编辑数据
     *
     * @param shareCommentReply 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public ResponseEntity<ShareCommentReply> edit(@RequestBody ShareCommentReply shareCommentReply) {
        return ResponseEntity.ok(this.shareCommentReplyService.update(shareCommentReply));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.shareCommentReplyService.deleteById(id));
    }

}

