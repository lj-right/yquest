package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.config.redis.RedisUtil;
import com.suifeng.yquest.dao.TrialCodeStatisticsDao;
import com.suifeng.yquest.entity.TrialCode;
import com.suifeng.yquest.dao.TrialCodeDao;
import com.suifeng.yquest.entity.TrialCodeStatistics;
import com.suifeng.yquest.service.TrialCodeService;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.*;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * (TrialCode)表服务实现类
 */
@Service("trialCodeService")
public class TrialCodeServiceImpl implements TrialCodeService {
    @Resource
    private TrialCodeDao trialCodeDao;
    @Resource
    private TrialCodeStatisticsDao trialCodeStatisticsDao;

    @Resource
    private RedisUtil redisUtil;

    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    private Random random = new Random();

    //试用码的数量
    private static final int batchSize = 5;

    //试用码的长度
    private static final int codeLength = 10;

    //过期时间(天数)
    private static final int expireHours = 7;

    //试用码的key
    private static final String TRIAL_CODE_KEY = "trial:codes";

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public TrialCode queryById(Integer id) {
        return this.trialCodeDao.queryById(id);
    }


    /**
     * 分页查询
     *
     * @param trialCode 筛选条件
     * @return 查询结果
     */
    @Override
    public PageResult<TrialCode> queryByPage(TrialCode trialCode) {
        PageResult<TrialCode> pageResult = new PageResult<>();
        pageResult.setPageNo(trialCode.getPageNo());
        pageResult.setPageSize(trialCode.getPageSize());
        int start = (trialCode.getPageNo() - 1) * trialCode.getPageSize();

        int count = trialCodeDao.countByCondition(trialCode);
        if (count == 0) {
            return pageResult;
        }
        List<TrialCode> trialCodeList = trialCodeDao.queryAllCode(start, trialCode.getPageSize());
        pageResult.setRecords(trialCodeList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 新增数据
     *
     * @param trialCode 实例对象
     * @return 实例对象
     */
    @Override
    public Boolean insert(TrialCode trialCode) {
        //设置ExpireTime默认值为当前时间的后7天
        Instant instant = LocalDate.now().plusDays(7)
                .atTime(LocalTime.MIDNIGHT)
                .atZone(ZoneId.systemDefault()).toInstant();
        trialCode.setExpireTime(Date.from(instant));
        trialCode.setStatus(0);
        trialCode.setCreatedTime(new Date());
        trialCode.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        return this.trialCodeDao.insert(trialCode) > 0;
    }

    /**
     * 批量插入数据
     */
    @Override
    public boolean insertBatch(Set<TrialCode> trialCodeList) {
        //设置ExpireTime默认值为当前时间的后7天
        Instant instant = LocalDate.now().plusDays(7)
                .atTime(LocalTime.MIDNIGHT)
                .atZone(ZoneId.systemDefault()).toInstant();
        trialCodeList.forEach(trialCode -> {
            trialCode.setExpireTime(Date.from(instant));
            trialCode.setStatus(0);
            trialCode.setCreatedTime(new Date());
            trialCode.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        });
        return this.trialCodeDao.insertBatch(trialCodeList) > 0;
    }

    /**
     * 批量修改数据
     */
    @Override
    public boolean update(List<TrialCode> trialCode) {
        return this.trialCodeDao.update(trialCode) > 0;
    }

    /**
     * 根据code数据
     */
    public boolean updateByCode(TrialCode trialCode) {
        return this.trialCodeDao.updateByCode(trialCode) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        TrialCode trialCode = this.queryById(id);
        String code = trialCode.getCode();
        boolean sRem = redisUtil.sRem(TRIAL_CODE_KEY, code) > 0;
        return this.trialCodeDao.deleteById(id) > 0 && sRem;
    }


    /**
     * 生成唯一试用码
     */
    @Transactional
    @Override
    public boolean generateTrialCodeBatch() {
        Set<String> codes = new HashSet<>();
        for (int i = 0; i < batchSize; i++) {
            String code = generateUniqueCode();
            codes.add(code);
        }
        // 将试用码添加到Redis Set中
        String[] codeArray = codes.toArray(new String[0]);


        //遍历codeArray数组获取code
        Set<TrialCode> CodeSet = new HashSet<>();
        for (int i = 0; i < codeArray.length; i++) {
            TrialCode trialCode = new TrialCode();
            trialCode.setCode(codeArray[i]);
            CodeSet.add(trialCode);
        }
        TrialCodeStatistics statistics = trialCodeStatisticsDao.queryById(1);
        if (statistics == null) {
            TrialCodeStatistics statistics1 = new TrialCodeStatistics();
            statistics1.setId(1);
            statistics1.setExpiredCount((long)0);
            statistics1.setAvailableCount((long)0);
            statistics1.setTotalGenerated((long)0);
            statistics1.setUsePercentage((double)0);
            trialCodeStatisticsDao.insert(statistics1);
        }
        TrialCodeStatistics result = new TrialCodeStatistics();
        result.setId(1);
        long avail = statistics.getAvailableCount() + batchSize;
        long total = statistics.getTotalGenerated() + batchSize;
        result.setAvailableCount(avail);
        result.setExpiredCount((statistics.getExpiredCount() != null && statistics.getExpiredCount() != 0) ? statistics.getExpiredCount() : 0);
        result.setTotalGenerated(total);
        result.setUsePercentage(Math.round((double) avail / total * 100.0) / 100.0);
        result.setUpdatedTime(new Date());

        boolean result2 = trialCodeStatisticsDao.update(result) > 0;
        boolean batch = insertBatch(CodeSet);

        redisUtil.sAdd(TRIAL_CODE_KEY, codeArray);
        // 设置过期时间
        Boolean expire = redisUtil.expire(TRIAL_CODE_KEY, expireHours, TimeUnit.DAYS);
        return batch && result2 && expire;
    }

    /**
     * 随机获取一个试用码
     */
    @Override
    public String getTrialCode() {
        try {
            String code = redisUtil.sPop(TRIAL_CODE_KEY);
            return code != null ? code : "";
        } catch (Exception e) {
            // 记录日志或进行其他异常处理
            return "";
        }
    }

    /**
     * 清理过期试用码(redis有过期时间)
     */
    @Override
    @Transactional
    public boolean cleanExpiredCodes() {
        TrialCode trialCode = new TrialCode();
        trialCode.setStatus(0);
        trialCode.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());  //默认未删除
        trialCode.setExpireTime(new Date());
        //查找出所有过期时间大于现在时间的数据
        Set<TrialCode> codeSet = trialCodeDao.queryExpiredCodes(trialCode);
        if (codeSet.isEmpty()) {
            return false;
        }
        //更新数据库
        codeSet.forEach(trialCode2 -> {
            trialCode2.setStatus(2);
            trialCode2.setUpdatedTime(new Date());
            trialCode2.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        });
        Set<String> ids = codeSet.stream()
                .map(trialCode3 -> String.valueOf(trialCode3.getId()))
                .collect(Collectors.toSet());

        List<TrialCode> codeList = new ArrayList<>(codeSet);
        boolean updated = this.update(codeList);

        TrialCodeStatistics statistics = lowTrialCodeStatistics(ids);

        int result = trialCodeStatisticsDao.update(statistics);


        return updated && result > 0;
    }


    /**
     * 消费试用码
     */
    @Override
    @Transactional
    public boolean ConsumeTrialCode(String code) {
        TrialCode trialCode = new TrialCode();
        trialCode.setCode(code);
        trialCode.setStatus(1);
        trialCode.setUpdatedTime(new Date());
        trialCode.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());

        boolean redisConsume = redisUtil.sRem(TRIAL_CODE_KEY, code) > 0;

        Set<String> CodeSet = new HashSet<>();
        CodeSet.add(code);
        TrialCodeStatistics statistics = lowTrialCodeStatistics(CodeSet);

        boolean result2 = trialCodeStatisticsDao.update(statistics) > 0;
        boolean updated = updateByCode(trialCode);
        return redisConsume && updated && result2;
    }

    //数据减少时更新TrialCodeStatistics
    @NotNull
    private TrialCodeStatistics lowTrialCodeStatistics(Set<String> trialCodeSet) {
        int length = trialCodeSet.stream().toArray().length;
        TrialCodeStatistics statistics = trialCodeStatisticsDao.queryById(1);
        long avail = statistics.getAvailableCount() - length;
        long expired = statistics.getExpiredCount() + length;
        statistics.setAvailableCount(avail);
        statistics.setExpiredCount(expired);
        statistics.setUsePercentage(Math.round((double) avail / statistics.getTotalGenerated() * 100.0) / 100.0);
        statistics.setUpdatedTime(new Date());
        return statistics;
    }


    //随机码去重
    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (redisUtil.sIsMember(TRIAL_CODE_KEY, code));// 检查是否已在可用集合或已使用集合中
        return code;
    }

    //生成随机码
    private String generateRandomCode() {
        StringBuilder sb = new StringBuilder(codeLength);
        for (int i = 0; i < codeLength; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }


}
