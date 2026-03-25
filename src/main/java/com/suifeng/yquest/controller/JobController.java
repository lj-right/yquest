package com.suifeng.yquest.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.Job;
import com.suifeng.yquest.service.JobService;
import com.suifeng.yquest.api.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (Job)表控制层
 */
@RestController
@RequestMapping("/job")
@CrossOrigin
public class JobController {
    /**
     * 服务对象
     */
    @Resource
    private JobService jobService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<Job> queryById(@PathVariable("id") Long id) {
        // 增加浏览次数
        jobService.incrementViewCount(id);
        return Result.ok(this.jobService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param job 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody Job job) {
        return Result.ok(this.jobService.insert(job));
    }

    /**
     * 编辑数据
     *
     * @param job 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody Job job) {
        return Result.ok(this.jobService.update(job));
    }

    /**
     * 删除数据
     *
     * @param job 实体
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteById")
    public Result<Boolean> deleteById(@RequestBody Job job) {
        return Result.ok(this.jobService.deleteById(job.getId()));
    }

    /**
     * 分页查询
     * @param job
     * @return
     */
    @PostMapping("/queryPage")
    public Result<PageResult<Job>> queryPage(@RequestBody Job job) {
        return Result.ok(this.jobService.queryByPage(job));
    }

    /**
     * 查询用户发布的职位
     *
     * @param job 查询条件
     * @return 分页结果
     */
    @PostMapping("/myJobs")
    public Result<PageResult<Job>> queryByUserId(@RequestBody Job job) {
        return Result.ok(this.jobService.queryByUserId(job));
    }

    /**
     * 根据公司ID查询职位
     *
     * @param job 查询条件
     * @return 分页结果
     */
    @PostMapping("/byCompany")
    public Result<PageResult<Job>> queryByCompanyId(@RequestBody Job job) {
        return Result.ok(this.jobService.queryByCompanyId(job));
    }

    /**
     * 审核职位
     *
     * @param id 职位ID
     * @param status 审核状态（1：已发布，2：已关闭）
     * @return 审核结果
     */
    @PostMapping("/audit/{id}/{status}")
    @SaCheckPermission({"manage"})
    public Result<Boolean> auditJob(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        return Result.ok(this.jobService.auditJob(id, status));
    }

}
