package com.suifeng.yquest.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.InterviewProcess;
import com.suifeng.yquest.service.InterviewProcessService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;

/**
 * (InterviewProcess)表控制层
 */
@RestController
@RequestMapping("/interview/process")
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
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteById(@PathVariable("id") Long id) {
        return Result.ok(this.interviewProcessService.deleteById(id));
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
     * 根据简历ID查询流程
     *
     * @param resumeId 简历ID
     * @return 流程
     */
    @GetMapping("/byResume/{resumeId}")
    public Result<InterviewProcess> queryByResumeId(@PathVariable("resumeId") Long resumeId) {
        return Result.ok(this.interviewProcessService.queryByResumeId(resumeId));
    }

    /**
     * 根据职位ID查询流程
     *
     * @param jobId 职位ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @PostMapping("/byJob/{jobId}/{pageNo}/{pageSize}")
    public Result<PageResult<InterviewProcess>> queryByJobId(@PathVariable("jobId") Long jobId, 
                                                           @PathVariable("pageNo") int pageNo, 
                                                           @PathVariable("pageSize") int pageSize) {
        return Result.ok(this.interviewProcessService.queryByJobId(jobId, pageNo, pageSize));
    }

    /**
     * 根据面试官ID查询流程
     *
     * @param interviewerId 面试官ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @PostMapping("/byInterviewer/{interviewerId}/{pageNo}/{pageSize}")
    public Result<PageResult<InterviewProcess>> queryByInterviewerId(@PathVariable("interviewerId") Long interviewerId, 
                                                                   @PathVariable("pageNo") int pageNo, 
                                                                   @PathVariable("pageSize") int pageSize) {
        return Result.ok(this.interviewProcessService.queryByInterviewerId(interviewerId, pageNo, pageSize));
    }

    /**
     * 推进面试流程
     *
     * @param id 流程ID
     * @param nextStage 下一阶段
     * @param nextStageTime 下一阶段时间
     * @param interviewerId 面试官ID
     * @param interviewerName 面试官姓名
     * @return 操作结果
     */
    @PostMapping("/advance/{id}/{nextStage}")
    public Result<Boolean> advanceProcess(@PathVariable("id") Long id, 
                                         @PathVariable("nextStage") Integer nextStage, 
                                         @RequestParam("nextStageTime") Date nextStageTime, 
                                         @RequestParam("interviewerId") Long interviewerId, 
                                         @RequestParam("interviewerName") String interviewerName) {
        return Result.ok(this.interviewProcessService.advanceProcess(id, nextStage, nextStageTime, interviewerId, interviewerName));
    }

    /**
     * 结束面试流程
     *
     * @param id 流程ID
     * @param status 状态（1：已完成，2：已取消）
     * @return 操作结果
     */
    @PostMapping("/end/{id}/{status}")
    public Result<Boolean> endProcess(@PathVariable("id") Long id, @PathVariable("status") Integer status) {
        return Result.ok(this.interviewProcessService.endProcess(id, status));
    }

    /**
     * 拒绝面试流程
     *
     * @param id 流程ID
     * @return 操作结果
     */
    @PostMapping("/reject/{id}")
    public Result<Boolean> rejectProcess(@PathVariable("id") Long id) {
        return Result.ok(this.interviewProcessService.rejectProcess(id));
    }

}
