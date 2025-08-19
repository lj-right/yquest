package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.ShareCircle;
import com.suifeng.yquest.service.ShareCircleService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 圈子表(ShareCircle)表控制层
 */
@RestController
@RequestMapping("shareCircle")
public class ShareCircleController {
    /**
     * 服务对象
     */
    @Resource
    private ShareCircleService shareCircleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ShareCircle> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.shareCircleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param shareCircle 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<ShareCircle> add(ShareCircle shareCircle) {
        return ResponseEntity.ok(this.shareCircleService.insert(shareCircle));
    }

    /**
     * 编辑数据
     *
     * @param shareCircle 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<ShareCircle> edit(ShareCircle shareCircle) {
        return ResponseEntity.ok(this.shareCircleService.update(shareCircle));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.shareCircleService.deleteById(id));
    }

}

