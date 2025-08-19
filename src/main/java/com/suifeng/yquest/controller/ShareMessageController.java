package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.ShareMessage;
import com.suifeng.yquest.service.ShareMessageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 信息提醒表(ShareMessage)表控制层
 */
@RestController
@RequestMapping("shareMessage")
public class ShareMessageController {
    /**
     * 服务对象
     */
    @Resource
    private ShareMessageService shareMessageService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ShareMessage> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.shareMessageService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param shareMessage 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<ShareMessage> add(ShareMessage shareMessage) {
        return ResponseEntity.ok(this.shareMessageService.insert(shareMessage));
    }

}

