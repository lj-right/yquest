package com.suifeng.yquest.controller;

import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.AuthRole;
import com.suifeng.yquest.service.AuthRoleService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 角色表(AuthRole)表控制层
 */
@RestController
@RequestMapping("/authRole")
public class AuthRoleController {
    /**
     * 服务对象
     */
    @Resource
    private AuthRoleService authRoleService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<AuthRole> queryById(@PathVariable("id") Long id) {
        return Result.ok(this.authRoleService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param authRole 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody AuthRole authRole) {
        return Result.ok(this.authRoleService.insert(authRole));
    }

}

