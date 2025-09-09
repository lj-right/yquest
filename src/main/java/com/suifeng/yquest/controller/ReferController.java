package com.suifeng.yquest.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
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
@CrossOrigin
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
    public ResponseEntity<Boolean> edit(@RequestBody Refer refer) {
        return ResponseEntity.ok(this.referService.update(refer)>0);
    }

    /**
     * 删除数据
     * 定时删除
     * @param refer 实体
     * @return 编辑结果
     */
    @DeleteMapping("/deleteById")
    public ResponseEntity<Boolean> deleteById(@RequestBody Refer refer) {
        return ResponseEntity.ok(this.referService.deleteById(refer) > 0);
    }


    /**
     * 分页查询
     * @param refer
     * @return
     */
    @PostMapping("/queryPage")
    public Result<PageResult<Refer>> queryPage(@RequestBody Refer refer){
        return Result.ok(this.referService.queryReferPage(refer));
    }

    /**
     * 分页查询待审核列表
     * @param refer
     * @return
     */
    @SaCheckPermission("manage_user")
    @PostMapping("/manageReferPage")
    public Result<PageResult<Refer>> manageReferPage(@RequestBody Refer refer){
        return Result.ok(this.referService.manageReferPage(refer));
    }
    /**
     * 分页搜索审核列表
     * @param refer
     * @return
     */
    @SaCheckPermission("manage_user")
    @PostMapping("/manageReferPage")
    public Result<PageResult<Refer>> searchByMessage(@RequestBody Refer refer){
        return Result.ok(this.referService.searchByMessage(refer));
    }

}

