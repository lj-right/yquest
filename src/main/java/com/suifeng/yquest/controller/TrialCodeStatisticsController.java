package com.suifeng.yquest.controller;

import com.suifeng.yquest.entity.TrialCodeStatistics;
import com.suifeng.yquest.service.TrialCodeStatisticsService;
import com.suifeng.yquest.api.common.Result;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * (TrialCodeStatistics)表控制层
 */
@RestController
@RequestMapping("trialCodeStatistics")
public class TrialCodeStatisticsController {
    /**
     * 服务对象
     */
    @Resource
    private TrialCodeStatisticsService trialCodeStatisticsService;


    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping("{id}")
    public Result<TrialCodeStatistics> queryById(@PathVariable("id") Integer id) {
        return Result.ok(this.trialCodeStatisticsService.queryById(id));
    }

    /**
     * 新增数据
     *
     * @param trialCodeStatistics 实体
     * @return 新增结果
     */
    @PostMapping("/add")
    public Result<TrialCodeStatistics> add(@RequestBody TrialCodeStatistics trialCodeStatistics) {
        return Result.ok(this.trialCodeStatisticsService.insert(trialCodeStatistics));
    }

    /**
     * 编辑数据
     *
     * @param trialCodeStatistics 实体
     * @return 编辑结果
     */
    @PutMapping("/edit")
    public Result<Boolean> edit(@RequestBody TrialCodeStatistics trialCodeStatistics) {
        return Result.ok(this.trialCodeStatisticsService.update(trialCodeStatistics));
    }

    /**
     * 删除数据
     *
     * @param id 主键
     * @return 删除是否成功
     */
    @DeleteMapping("/delete")
    public Result<Boolean> deleteById(@Param("id") Integer id) {
        return Result.ok(this.trialCodeStatisticsService.deleteById(id));
    }

}

