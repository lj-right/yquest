package com.suifeng.yquest.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.InterviewProcess;
import com.suifeng.yquest.service.InterviewProcessService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (InterviewProcess)表控制层
 */
@RestController
@RequestMapping("/interviewProcess")
@CrossOrigin
public class InterviewProcessController {
    /**
     * 服务对象
     */
    @Resource
    private InterviewProcessService interviewProcessService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<InterviewProcess> queryById(@PathVariable("id") Long id) {
        return Result.ok(this.interviewProcessService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param interviewProcess 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody InterviewProcess interviewProcess) {
        return Result.ok(this.interviewProcessService.insert(interviewProcess));
    }

    /**
     * 编辑数据
     *
     * @param interviewProcess 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody InterviewProcess interviewProcess) {
        return Result.ok(this.interviewProcessService.update(interviewProcess));
    }

    /**
     * 删除数据
     *
     * @param interviewProcess 实体
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteById")
    public Result<Boolean> deleteById(@RequestBody InterviewProcess interviewProcess) {
        return Result.ok(this.interviewProcessService.deleteById(interviewProcess.getId()));
    }

    /**
     * 分页查询
     * @param interviewProcess
     * @return
     */
    @PostMapping("/queryPage")
    public Result<PageResult<InterviewProcess>> queryPage(@RequestBody InterviewProcess interviewProcess) {
        return Result.ok(this.interviewProcessService.queryByPage(interviewProcess));
    }

    /**
     * 根据简历ID查询面试流程
     *
     * @param resumeId 简历ID
     * @return 面试流程
     */
    @GetMapping("/byResume/{resumeId}")
    public Result<InterviewProcess> queryByResumeId(@PathVariable("resumeId") Long resumeId) {
        return Result.ok(this.interviewProcessService.queryByResumeId(resumeId));
    }

    /**
     * 更新面试流程状态
     *
     * @param interviewProcess 面试流程
     * @return 更新结果
     */
    @PostMapping("/updateStatus")
    public Result<Boolean> updateStatus(@RequestBody InterviewProcess interviewProcess) {
        return Result.ok(this.interviewProcessService.updateStatus(interviewProcess.getId(), interviewProcess.getStatus()));
    }

}
