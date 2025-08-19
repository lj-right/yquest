package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.AuthPermission;
import com.suifeng.yquest.service.AuthPermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 权限表(AuthPermission)表控制层
 */
@RestController
@RequestMapping("authPermission")
public class AuthPermissionController {
    /**
     * 服务对象
     */
    @Resource
    private AuthPermissionService authPermissionService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<AuthPermission> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.authPermissionService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param authPermission 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<AuthPermission> add(AuthPermission authPermission) {
        return ResponseEntity.ok(this.authPermissionService.insert(authPermission));
    }

}

