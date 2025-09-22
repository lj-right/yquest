package com.suifeng.yquest.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.google.common.base.Preconditions;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.Refer;
import com.suifeng.yquest.service.ReferService;
import org.apache.commons.lang3.StringUtils;
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
    public Result<Boolean> add(@RequestBody Refer refer) {
        return Result.ok(this.referService.insert(refer));
    }

    /**
     * 编辑数据
     *
     * @param refer 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody Refer refer) {
        return Result.ok(this.referService.update(refer));
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
        try {
            Preconditions.checkArgument(!StringUtils.isBlank(refer.getCompany()), "公司名称不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(refer.getRefercode()), "内推码不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(refer.getIndustry()), "行业不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(refer.getName()), "发起人不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(refer.getEmail()), "发起人邮箱不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(refer.getDescription()), "描述不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(refer.getUrl()), "内推链接不能为空");
            return Result.ok(this.referService.manageReferPage(refer));
        } catch (Exception e) {
            return Result.fail("新内推提交失败");
        }
    }
    /**
     * 分页搜索审核列表
     * @param refer
     * @return
     */
    @PostMapping("/searchByMessage")
    public Result<PageResult<Refer>> searchByMessage(@RequestBody Refer refer){
        return Result.ok(this.referService.searchByMessage(refer));
    }

}

