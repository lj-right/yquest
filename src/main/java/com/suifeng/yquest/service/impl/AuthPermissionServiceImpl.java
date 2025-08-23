package com.suifeng.yquest.service.impl;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.AuthPermission;
import com.suifeng.yquest.dao.AuthPermissionDao;
import com.suifeng.yquest.redis.RedisUtil;
import com.suifeng.yquest.service.AuthPermissionService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限表(AuthPermission)表服务实现类
 */
@Service("authPermissionService")
public class AuthPermissionServiceImpl implements AuthPermissionService {
    @Resource
    private AuthPermissionDao authPermissionDao;

    @Resource
    private RedisUtil redisUtil;

    private String authPermissionPrefix = "auth.permission";

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthPermission queryById(Long id) {
        return this.authPermissionDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(AuthPermission authPermission) {
        authPermission.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return this.authPermissionDao.insert(authPermission) > 0;
    }

    @Override
    public List<String> getPermission(AuthPermission authPermission) {
        String permissionKey = redisUtil.buildKey(authPermissionPrefix, authPermission.getName());
        String permissionValue = redisUtil.get(permissionKey);
        if (StringUtils.isBlank(permissionValue)) {
            return Collections.emptyList();
        }
        List<AuthPermission> permissionList = new Gson().fromJson(permissionValue,
                new TypeToken<List<AuthPermission>>() {
                }.getType());
        List<String> authList = permissionList.stream().map(AuthPermission::getPermissionKey).collect(Collectors.toList());
        return authList;
    }

    @Override
    public List<AuthPermission> queryByRoleList(List<Long> permissionIdList) {
        return this.authPermissionDao.queryByRoleList(permissionIdList);
    }

}
