package com.suifeng.yquest.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.Company;
import com.suifeng.yquest.service.CompanyService;
import com.suifeng.yquest.api.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Company)表控制层
 */
@RestController
@RequestMapping("/company")
@CrossOrigin
public class CompanyController {
    /**
     * 服务对象
     */
    @Resource
    private CompanyService companyService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<Company> queryById(@PathVariable("id") Long id) {
        return Result.ok(this.companyService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param company 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody Company company) {
        return Result.ok(this.companyService.insert(company));
    }

    /**
     * 编辑数据
     *
     * @param company 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody Company company) {
        return Result.ok(this.companyService.update(company));
    }

    /**
     * 删除数据
     *
     * @param company 实体
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteById")
    public Result<Boolean> deleteById(@RequestBody Company company) {
        return Result.ok(this.companyService.deleteById(company.getId()));
    }

    /**
     * 分页查询
     * @param company
     * @return
     */
    @PostMapping("/queryPage")
    public Result<PageResult<Company>> queryPage(@RequestBody Company company) {
        return Result.ok(this.companyService.queryByPage(company));
    }

    /**
     * 审核公司
     *
     * @param id 公司ID
     * @param status 审核状态（1：已通过，0：未通过）
     * @return 审核结果
     */
    @PostMapping("/audit/{id}/{status}")
    @SaCheckPermission({"manage"})
    public Result<Boolean> auditCompany(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        return Result.ok(this.companyService.auditCompany(id, status));
    }

}
