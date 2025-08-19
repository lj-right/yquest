package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.AuthUserRole;
import com.suifeng.yquest.service.AuthUserRoleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户角色表(AuthUserRole)表控制层
 */
@RestController
@RequestMapping("authUserRole")
public class AuthUserRoleController {
    /**
     * 服务对象
     */
    @Resource
    private AuthUserRoleService authUserRoleService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<AuthUserRole> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.authUserRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param authUserRole 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<AuthUserRole> add(AuthUserRole authUserRole) {
        return ResponseEntity.ok(this.authUserRoleService.insert(authUserRole));
    }
}

