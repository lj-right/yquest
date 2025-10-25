package com.suifeng.yquest.task;

import com.suifeng.yquest.entity.TrialCodeStatistics;
import com.suifeng.yquest.service.TrialCodeService;
import com.suifeng.yquest.service.TrialCodeStatisticsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@EnableScheduling //开启任务调度
@Component
@Slf4j
public class TrialCodeTask {

    @Autowired
    private TrialCodeService trialCodeService;

    @Autowired
    private TrialCodeStatisticsService trialCodeStatisticsService;


    /**
     * 每天凌晨2点生成试用码
     */
    @Scheduled(cron = "0 0 2 * * *")
//    @Scheduled(cron = "0 0/1 * * * ? ")
    public void dailyTrialCodeGeneration() {
        log.info("开始执行每日试用码生成任务");
        trialCodeService.generateTrialCodeBatch();
    }

    /**
     * 每小时检查试用码数量，不足时补充
     */
    @Scheduled(cron = "0 0 * * * *")
//    @Scheduled(cron = "0 0/1 * * * ? ")
    public void checkAndReplenishCodes() {
        TrialCodeStatistics stats = trialCodeStatisticsService.queryById(1);

        if (stats.getUsePercentage() < 0.3) {
            log.info("试用码数量不足30%，自动补充。");
            trialCodeService.generateTrialCodeBatch();
        }
    }

    /**
     * 每天凌晨3点清理过期试用码
     */
    @Scheduled(cron = "0 0 3 * * *")
//    @Scheduled(cron = "0 0/1 * * * ? ")
    public void cleanExpiredTrialCodes() {
        log.info("开始执行过期试用码清理任务");
        trialCodeService.cleanExpiredCodes();
    }

}
