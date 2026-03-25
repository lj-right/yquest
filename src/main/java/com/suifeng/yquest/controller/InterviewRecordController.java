package com.suifeng.yquest.controller;


import cn.dev33.satoken.annotation.SaCheckPermission;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.common.Result;
import com.suifeng.yquest.entity.InterviewRecord;
import com.suifeng.yquest.service.InterviewRecordService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (InterviewRecord)表控制层
 */
@RestController
@RequestMapping("/interview/record")
@CrossOrigin
public class InterviewRecordController {
    /**
     * 服务对象
     */
    @Resource
    private InterviewRecordService interviewRecordService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<InterviewRecord> queryById(@PathVariable("id") Long id) {
        return Result.ok(this.interviewRecordService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param interviewRecord 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody InterviewRecord interviewRecord) {
        return Result.ok(this.interviewRecordService.insert(interviewRecord));
    }

    /**
     * 编辑数据
     *
     * @param interviewRecord 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody InterviewRecord interviewRecord) {
        return Result.ok(this.interviewRecordService.update(interviewRecord));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete/{id}")
    public Result<Boolean> deleteById(@PathVariable("id") Long id) {
        return Result.ok(this.interviewRecordService.deleteById(id));
    }

    /**
     * 分页查询
     * @param interviewRecord
     * @return
     */
    @PostMapping("/queryPage")
    public Result<PageResult<InterviewRecord>> queryPage(@RequestBody InterviewRecord interviewRecord) {
        return Result.ok(this.interviewRecordService.queryByPage(interviewRecord));
    }

    /**
     * 根据流程ID查询面试记录
     *
     * @param processId 流程ID
     * @return 面试记录列表
     */
    @GetMapping("/byProcess/{processId}")
    public Result<java.util.List<InterviewRecord>> queryByProcessId(@PathVariable("processId") Long processId) {
        return Result.ok(this.interviewRecordService.queryByProcessId(processId));
    }

    /**
     * 根据职位ID查询面试记录
     *
     * @param jobId 职位ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @PostMapping("/byJob/{jobId}/{pageNo}/{pageSize}")
    public Result<PageResult<InterviewRecord>> queryByJobId(@PathVariable("jobId") Long jobId, 
                                                         @PathVariable("pageNo") int pageNo, 
                                                         @PathVariable("pageSize") int pageSize) {
        return Result.ok(this.interviewRecordService.queryByJobId(jobId, pageNo, pageSize));
    }

    /**
     * 根据面试官ID查询面试记录
     *
     * @param interviewerId 面试官ID
     * @param pageNo 页码
     * @param pageSize 每页大小
     * @return 分页结果
     */
    @PostMapping("/byInterviewer/{interviewerId}/{pageNo}/{pageSize}")
    public Result<PageResult<InterviewRecord>> queryByInterviewerId(@PathVariable("interviewerId") Long interviewerId, 
                                                                   @PathVariable("pageNo") int pageNo, 
                                                                   @PathVariable("pageSize") int pageSize) {
        return Result.ok(this.interviewRecordService.queryByInterviewerId(interviewerId, pageNo, pageSize));
    }

    /**
     * 评估面试结果
     *
     * @param id 记录ID
     * @param result 结果（1：通过，2：未通过）
     * @param comments 评语
     * @return 操作结果
     */
    @PostMapping("/evaluate/{id}/{result}")
    public Result<Boolean> evaluateInterview(@PathVariable("id") Long id, 
                                           @PathVariable("result") Integer result, 
                                           @RequestParam("comments") String comments) {
        return Result.ok(this.interviewRecordService.evaluateInterview(id, result, comments));
    }

}
