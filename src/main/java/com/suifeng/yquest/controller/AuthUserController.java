package com.suifeng.yquest.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.AuthUser;
import com.suifeng.yquest.service.AuthUserService;
import com.suifeng.yquest.service.impl.FileService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

/**
 * 用户信息表(AuthUser)表控制层
 */
@RestController
@RequestMapping("/authUser")
@Slf4j
@CrossOrigin
public class AuthUserController {
    /**
     * 服务对象
     */
    @Resource
    private AuthUserService authUserService;

    @Resource
    private FileService fileService;

    @Value("${storage.service.type}")
    private String storageType;

    /**
     * 通过email查询单条数据
     */
    @PostMapping("/queryByEmail")
    public Result<AuthUser> queryByEmail(@RequestBody AuthUser authUser) {
        return Result.ok(authUserService.queryByEmail(authUser));
    }
    /**
     * 通过name查询单条数据
     */
    @PostMapping("/queryByUserName")
    public Result<AuthUser> queryByName(@RequestBody AuthUser authUser) {
        return Result.ok(authUserService.queryByName(authUser));
    }
    /**
     * 通过email获取nickname
     */
    @GetMapping("/queryNickNameByEmail")
    public Result<String> queryNickNameByEmail(@RequestParam("email") String email) {
        return Result.ok(authUserService.queryNickNameByEmail(email));
    }

    /**
     * 注册用户
     */
    @PostMapping("/no-auth/register")
    public Result<Boolean> add(@RequestBody AuthUser user) {
        try {
            if (log.isInfoEnabled()) {
                log.info("新增用户入参{}", JSON.toJSONString(user));
            }
            Preconditions.checkArgument(!StringUtils.isBlank(user.getNickName()), "别名不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(user.getPassword()), "密码不能为空");
            return Result.ok(authUserService.insert(user));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("新增用户异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 编辑数据
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody AuthUser authUser) {
        return Result.ok(authUserService.update(authUser));
    }

    /**
     * 删除数据
     */
    @DeleteMapping("/deleteById/{id}")
    public Result<Boolean> deleteById(@PathVariable("id") Long id) {
        return Result.ok(authUserService.deleteById(id));
    }
    /**
     * 分页查询用户
     */
    @SaCheckPermission({"manage_user"})
    @PostMapping("/queryPage")
    public Result<PageResult<AuthUser>> queryPage(@RequestBody AuthUser authUser) {
        return Result.ok(authUserService.queryPage(authUser));
    }

    /**
     * 注册 | 忘记密码：发送邮箱验证码
     */
    @PostMapping("/email/send")
    public Result<Boolean> sendEmail(@RequestBody AuthUser user) {
        try {
            if (log.isInfoEnabled()) {
                log.info("发送邮箱验证码{}", JSON.toJSONString(user));
            }
            Preconditions.checkArgument(!StringUtils.isBlank(user.getEmail()), "邮箱不能为空");
            return Result.ok(authUserService.sendEmail(user));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("发送邮箱验证码！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 登录：发送邮箱验证码
     */
    @PostMapping("/email/sendToLogin")
    public Result<Boolean> sendToLogin(@RequestBody AuthUser user) {
        try {
            if (log.isInfoEnabled()) {
                log.info("发送邮箱验证码{}", JSON.toJSONString(user));
            }
            Preconditions.checkArgument(!StringUtils.isBlank(user.getEmail()), "邮箱不能为空");
            return Result.ok(authUserService.sendEmailtoLogin(user));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("发送邮箱验证码！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 注册 | 忘记密码: 校验用户邮箱验证码
     */
    @PostMapping("/auth/captcha")
    public Result<Boolean> getEmailcaptcha(@RequestBody AuthUser user) {
        try {
            if (log.isInfoEnabled()) {
                log.info("获取邮箱验证码{}", JSON.toJSONString(user));
            }
            Preconditions.checkArgument(!StringUtils.isBlank(user.getEmail()), "邮箱不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(user.getExtJson()), "验证码不能为空");
            return Result.ok(authUserService.getEmailcaptcha(user));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("获取邮箱验证码！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }
    /**
     * 登录: 校验用户邮箱验证码
     * 该功能已在LoginByEmail类中实现
     * 可以不用调用
     */
    @PostMapping("/auth/captchaToLogin")
    public Result<Boolean> captchaToLogin(@RequestBody AuthUser user) {
        try {
            if (log.isInfoEnabled()) {
                log.info("获取邮箱验证码{}", JSON.toJSONString(user));
            }
            Preconditions.checkArgument(!StringUtils.isBlank(user.getEmail()), "邮箱不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(user.getExtJson()), "验证码不能为空");
            return Result.ok(authUserService.getLoginEmailCaptcha(user));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("获取邮箱验证码！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }



    /**
     * 上传文件
     */
    @PostMapping("/user/uploadFile")
    public Result<String> uploadFile(@RequestBody MultipartFile uploadFile) {
        try {
            return Result.ok(fileService.uploadFile(uploadFile,"avator","icon"));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("上传文件错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }
    /**
     * 删除oss指定文件
     */
    @DeleteMapping("/oss/deletefile")
    public Result deletefile(@RequestBody AuthUser assets) {
        try {
            fileService.deletefile(assets);
            return Result.ok();
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("删除oss指定文件错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }
    /**
     * 统一的登录
     */
    @PostMapping("/auth/login")
    public Result<SaResult> dologin(@RequestBody AuthUser user) {
        try {
            return Result.ok( authUserService.doLogin(user));
        } catch (Exception e) {
            log.error("登录错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }
    /**
     *  这是用户 已经存在于数据库中 的时候
     *  刷新缓存中对应用户的角色和权限
     */
    @PostMapping("/auth/refreshRedis")
    public Result<Boolean> refreshRedis(@RequestBody AuthUser user) {
        try {
            return Result.ok( authUserService.refreshRedis(user));
        }catch (Exception e) {
            log.error("登录错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }


    /**
     * 退出登录
     */
    @PostMapping("/auth/exit-login")
    public Result<SaResult> exitlogin(@RequestBody AuthUser user) {
        try {
            log.info("UserController.exitlogin.userEmail:{}", user.getEmail());
            Preconditions.checkArgument(!StringUtils.isBlank(user.getEmail()), "邮箱不能为空");
            StpUtil.logout(user.getEmail());
            return Result.ok();
        } catch (Exception e) {
            log.error("退出登录错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    /**
     * 忘记密码
     */
    @PostMapping("/auth/forgetPwd")
    public Result<SaResult> forgetPwd(@RequestBody AuthUser user) {
        try {
            log.info("UserController.forgetPwd.userEmail:{}", user.getEmail());
            Preconditions.checkArgument(!StringUtils.isBlank(user.getEmail()), "邮箱不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(user.getExtJson()), "验证码不能为空");
            return Result.ok(authUserService.forgetPwd(user));
        } catch (Exception e) {
            log.error("忘记密码错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        }
    }

    @GetMapping("/auth/isLogin")
    public Result<Boolean> isLogin(){
       return Result.ok(authUserService.isLogin());
    }
}

