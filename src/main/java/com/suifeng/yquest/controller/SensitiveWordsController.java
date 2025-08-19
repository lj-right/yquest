package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.SensitiveWords;
import com.suifeng.yquest.service.SensitiveWordsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 敏感词表(SensitiveWords)表控制层
 */
@RestController
@RequestMapping("sensitiveWords")
public class SensitiveWordsController {
    /**
     * 服务对象
     */
    @Resource
    private SensitiveWordsService sensitiveWordsService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<SensitiveWords> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.sensitiveWordsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param sensitiveWords 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<SensitiveWords> add(SensitiveWords sensitiveWords) {
        return ResponseEntity.ok(this.sensitiveWordsService.insert(sensitiveWords));
    }

    /**
     * 编辑数据
     *
     * @param sensitiveWords 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<SensitiveWords> edit(SensitiveWords sensitiveWords) {
        return ResponseEntity.ok(this.sensitiveWordsService.update(sensitiveWords));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.sensitiveWordsService.deleteById(id));
    }

}

