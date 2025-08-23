package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.Refer;
import com.suifeng.yquest.service.ReferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Refer)表控制层
 */
@RestController
@RequestMapping("/refer")
public class ReferController {
    /**
     * 服务对象
     */
    @Resource
    private ReferService referService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<Refer> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.referService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param refer 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public ResponseEntity<Refer> add(@RequestBody Refer refer) {
        return ResponseEntity.ok(this.referService.insert(refer));
    }

    /**
     * 编辑数据
     *
     * @param refer 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public ResponseEntity<Refer> edit(@RequestBody Refer refer) {
        return ResponseEntity.ok(this.referService.update(refer));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(Long id) {
        return ResponseEntity.ok(this.referService.deleteById(id));
    }

}

