package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.ShareMoment;
import com.suifeng.yquest.service.ShareMomentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 评论表(ShareMoment)表控制层
 */
@RestController
@RequestMapping("/shareMoment")
public class ShareMomentController {
    /**
     * 服务对象
     */
    @Resource
    private ShareMomentService shareMomentService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<ShareMoment> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.shareMomentService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param shareMoment 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseEntity<ShareMoment> add(@RequestBody ShareMoment shareMoment) {
        return ResponseEntity.ok(this.shareMomentService.insert(shareMoment));
    }

    /**
     * 编辑数据
     *
     * @param shareMoment 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public ResponseEntity<ShareMoment> edit(@RequestBody ShareMoment shareMoment) {
        return ResponseEntity.ok(this.shareMomentService.update(shareMoment));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.shareMomentService.deleteById(id));
    }

}

