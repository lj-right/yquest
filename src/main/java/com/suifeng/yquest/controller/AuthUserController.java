package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.AuthUser;
import com.suifeng.yquest.service.AuthUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 用户信息表(AuthUser)表控制层
 */
@RestController
@RequestMapping("/authUser")
public class AuthUserController {
    /**
     * 服务对象
     */
    @Resource
    private AuthUserService authUserService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public ResponseEntity<AuthUser> queryById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(this.authUserService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param authUser 实体
     * @return 新增结果
     */
    @PostMapping
    public ResponseEntity<AuthUser> add(AuthUser authUser) {
        return ResponseEntity.ok(this.authUserService.insert(authUser));
    }

    /**
     * 编辑数据
     *
     * @param authUser 实体
     * @return 编辑结果
     */
    @PutMapping
    public ResponseEntity<AuthUser> edit(AuthUser authUser) {
        return ResponseEntity.ok(this.authUserService.update(authUser));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping
    public ResponseEntity<Boolean> deleteById(Integer id) {
        return ResponseEntity.ok(this.authUserService.deleteById(id));
    }

}

