package com.suifeng.yquest.controller;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.entity.TrialCode;
import com.suifeng.yquest.service.TrialCodeService;
import com.suifeng.yquest.api.common.Result;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Set;

/**
 * (TrialCode)表控制层
 */
@RestController
@RequestMapping("trialCode")
public class TrialCodeController {
    /**
     * 服务对象
     */
    @Resource
    private TrialCodeService trialCodeService;

    /**
     * 分页查询
     *
     * @param trialCode   筛选条件
     * @return 查询结果
     */
    @PostMapping("/getPage")
    public Result<PageResult<TrialCode>> queryByPage(@RequestBody TrialCode trialCode) {
        return Result.ok(this.trialCodeService.queryByPage(trialCode));
    }

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<TrialCode> queryById(@PathVariable("id") Integer id) {
        return Result.ok(this.trialCodeService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param trialCode 实体
     * @return 新增结果
     */
    @PostMapping("/insert")
    public Result<Boolean> add(@RequestBody TrialCode trialCode) {
        return Result.ok(this.trialCodeService.insert(trialCode));
    }

    /**
     * 编辑数据(批量)
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody Set<TrialCode> trialCodeSet) {
        return Result.ok(this.trialCodeService.update(trialCodeSet));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    public Result<Boolean> deleteById(@RequestParam("id") Integer id) {
        return Result.ok(this.trialCodeService.deleteById(id));
    }

    /**
     * 消费试用码
     */
    @PostMapping("/consume")
    public Result<Boolean> consume(@RequestParam("trialCode") Set<String> trialCodeSet) {
        return Result.ok(this.trialCodeService.ConsumeTrialCode(trialCodeSet));
    }
}

