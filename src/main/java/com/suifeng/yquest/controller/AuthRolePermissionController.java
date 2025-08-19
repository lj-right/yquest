package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.AuthRolePermission;
import com.suifeng.yquest.service.AuthRolePermissionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色权限关联表(AuthRolePermission)表控制层
 */
@RestController
@RequestMapping("authRolePermission")
public class AuthRolePermissionController {
    /**
     * 服务对象
     */
    @Resource
    private AuthRolePermissionService authRolePermissionService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<AuthRolePermission> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.authRolePermissionService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param authRolePermission 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<AuthRolePermission> add(AuthRolePermission authRolePermission) {
        return ResponseEntity.ok(this.authRolePermissionService.insert(authRolePermission));
    }
}

