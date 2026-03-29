package com.suifeng.yquest.controller;

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
@RequestMapping("/interviewRecord")
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
     * @param interviewRecord 实体
     * @return 删除是否成功
     */
    @DeleteMapping("/deleteById")
    public Result<Boolean> deleteById(@RequestBody InterviewRecord interviewRecord) {
        return Result.ok(this.interviewRecordService.deleteById(interviewRecord.getId()));
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
     * 根据面试流程ID查询面试记录
     *
     * @param interviewRecord 查询条件
     * @return 分页结果
     */
    @PostMapping("/byProcess")
    public Result<PageResult<InterviewRecord>> queryByProcessId(@RequestBody InterviewRecord interviewRecord) {
        return Result.ok(this.interviewRecordService.queryByProcessId(interviewRecord.getProcessId()));
    }

}
