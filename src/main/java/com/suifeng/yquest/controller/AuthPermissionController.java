package com.suifeng.yquest.controller;

import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.AuthPermission;
import com.suifeng.yquest.service.AuthPermissionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 权限表(AuthPermission)表控制层
 */
@RestController
@RequestMapping("/permission")
@Slf4j
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
    @GetMapping("/{id}")
    public ResponseEntity<AuthPermission> queryById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(this.authPermissionService.queryById(id));
    }

    /**
     * 新增权限
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody AuthPermission authPermission) {
        try {
            if (log.isInfoEnabled()) {
                log.info("PermissionController.add.dto:{}", JSON.toJSONString(authPermission));
            }
            Preconditions.checkArgument(!StringUtils.isBlank(authPermission.getName()), "权限名称不能为空");
            Preconditions.checkNotNull(authPermission.getParentId(), "权限父id不能为空");
            return Result.ok(this.authPermissionService.insert(authPermission));
        } catch (Exception e) {
            log.error("PermissionController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增权限失败");
        }
    }

    /**
     * 查询用户权限
     */
    @PostMapping("/getPermission")
    public Result<Boolean> getPermission(@RequestBody AuthPermission authPermission) {
        try {
            log.info("PermissionController.getPermission.id:{}",authPermission.getId());
            Preconditions.checkArgument(!StringUtils.isBlank(authPermission.getName()), "用户名称不能为空");
            return Result.ok(this.authPermissionService.getPermission(authPermission));
        } catch (Exception e) {
            log.error("PermissionController.getPermission.error:{}", e.getMessage(), e);
            return Result.fail("查询用户权限信息失败");
        }
    }

}

