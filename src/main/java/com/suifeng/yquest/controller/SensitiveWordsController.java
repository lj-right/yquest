package com.suifeng.yquest.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.base.Preconditions;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.SensitiveWords;
import com.suifeng.yquest.service.SensitiveWordsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * 圈子信息
 */
@Slf4j
@RestController
@RequestMapping("/circle/sensitive/words")
public class SensitiveWordsController {

    @Resource
    private SensitiveWordsService sensitiveWordsService;

    /**
     * 新增敏感词
     */
    @SaCheckPermission({"manage_user"})
    @GetMapping(value = "/save")
    public Result<Boolean> save(String words, Integer type) {
        try {
            if (log.isInfoEnabled()) {
                log.info("新增敏感词入参{}", words);
            }
            Preconditions.checkArgument(StringUtils.isNotBlank(words), "参数不能为空！");
            SensitiveWords data = new SensitiveWords();
            data.setType(type);
            data.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            data.setWords(words);
            return Result.ok(sensitiveWordsService.save(data));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("新增敏感词异常！错误原因{}", e.getMessage(), e);
            return Result.fail("新增敏感词异常！");
        }
    }

    /**
     * 删除敏感词
     */
    @SaCheckPermission({"manage_user"})
    @GetMapping(value = "/remove")
    public Result<Boolean> remove(Long id) {
        try {
            if (log.isInfoEnabled()) {
                log.info("删除敏感词入参{}", id);
            }
            Preconditions.checkArgument(Objects.nonNull(id), "参数不能为空！");
            LambdaUpdateWrapper<SensitiveWords> update = Wrappers.<SensitiveWords>lambdaUpdate().set(SensitiveWords::getIsDeleted, IsDeletedFlagEnum.DELETED.getCode())
                    .eq(SensitiveWords::getId, id).eq(SensitiveWords::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode());
            return Result.ok(sensitiveWordsService.update(update));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("删除敏感词异常！错误原因{}", e.getMessage(), e);
            return Result.fail("删除敏感词异常！");
        }
    }

    /**
     * 更新敏感词
     */
    @SaCheckPermission({"manage_user"})
    @PostMapping(value = "/update")
    public Result<Boolean> update(@RequestBody SensitiveWords sensitiveWords) {
        try {
            if (log.isInfoEnabled()) {
                log.info("修改敏感词入参{}", sensitiveWords);
            }
            Preconditions.checkArgument(Objects.nonNull(sensitiveWords.getId()), "id参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(sensitiveWords.getWords()), "敏感词参数不能为空！");
            Preconditions.checkArgument(Objects.nonNull(sensitiveWords.getType()), "类型参数不能为空！");
            LambdaUpdateWrapper<SensitiveWords> update = Wrappers.<SensitiveWords>lambdaUpdate()
                    .eq(SensitiveWords::getId, sensitiveWords.getId())
                    .set(SensitiveWords::getWords, sensitiveWords.getWords())
                    .set(SensitiveWords::getType, sensitiveWords.getType())
                    .eq(SensitiveWords::getIsDeleted, IsDeletedFlagEnum.UN_DELETED.getCode());
            return Result.ok(sensitiveWordsService.update(update));
        } catch (IllegalArgumentException e) {
            log.error("参数异常！错误原因{}", e.getMessage(), e);
            return Result.fail(e.getMessage());
        } catch (Exception e) {
            log.error("修改敏感词异常！错误原因{}", e.getMessage(), e);
            return Result.fail("删除敏感词异常！");
        }
    }

    /**
     * 敏感词分页查询
     */

    @SaCheckPermission({"manage_user"})
    @PostMapping(value = "/queryPage")
    public Result<PageResult<SensitiveWords>> queryPage(@RequestBody SensitiveWords sw) {
        try {
            return Result.ok(sensitiveWordsService.queryPage(sw));
        } catch (Exception e) {
            log.error("分页查询敏感词异常！错误原因{}", e.getMessage(), e);
            return Result.fail("分页查询敏感词异常！");
        }
    }

}
