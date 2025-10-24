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

    //试用码已使用的key
    private static final String TRIAL_CODE_USED_KEY = "trial:code:used";

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
        trialCode.setStatus(1);
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
            trialCode.setStatus(1);
            trialCode.setCreatedTime(new Date());
            trialCode.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        });
        return this.trialCodeDao.insertBatch(trialCodeList) > 0;
    }

    /**
     * 批量修改数据
     */
    @Override
    public boolean update(Set<TrialCode> trialCodeSet) {
        return this.trialCodeDao.update(trialCodeSet) > 0 ;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.trialCodeDao.deleteById(id) > 0;
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
        redisUtil.sAdd(TRIAL_CODE_KEY, codeArray);
        // 设置过期时间
        redisUtil.expire(TRIAL_CODE_KEY, expireHours, TimeUnit.DAYS);

        //遍历codeArray数组获取code
        Set<TrialCode> CodeSet = new HashSet<>();
        for (int i = 0; i < codeArray.length; i++) {
            TrialCode trialCode = new TrialCode();
            trialCode.setCode(codeArray[i]);
            CodeSet.add(trialCode);
        }
        TrialCodeStatistics statistics = trialCodeStatisticsDao.queryById(1);
        TrialCodeStatistics result = new TrialCodeStatistics();
        long avail = statistics.getAvailableCount() + batchSize;
        long total = statistics.getTotalGenerated() + batchSize;
        result.setAvailableCount(avail);
        result.setTotalGenerated(total);
        result.setUsePercentage((double) avail / total);
        result.setUpdatedTime(new Date());

        boolean result2 = trialCodeStatisticsDao.update(result) > 0;
        boolean batch = insertBatch(CodeSet);
        return batch && result2;
    }

    /**
     * 清理过期试用码(redis有过期时间)
     */
    @Override
    @Transactional
    public boolean cleanExpiredCodes() {
        TrialCode trialCode = new TrialCode();
        trialCode.setUpdatedTime(new Date());
        trialCode.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        //查找出所有过期时间大于现在时间的数据
        Set<TrialCode> trialCodeSet = trialCodeDao.queryExpiredCodes(trialCode);
        //更新数据库
        trialCodeSet.forEach(trialCode2 -> {
            trialCode2.setStatus(2);
            trialCode2.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        });
        boolean update = update(trialCodeSet);

        Set<String> ids = trialCodeSet.stream()
                .map(trialCode3 -> String.valueOf(trialCode3.getId()))
                .collect(Collectors.toSet());
        TrialCodeStatistics statistics = lowTrialCodeStatistics(ids);

        int result = trialCodeStatisticsDao.update(statistics);


        return update && result > 0 ;
    }


    /**
     * 消费试用码
     */
    @Override
    public boolean ConsumeTrialCode(Set<String> trialCodeSet){
        Set<TrialCode> trialCodeList = new HashSet<>();
        trialCodeSet.forEach(code -> {
            TrialCode trialCode1 = new TrialCode();
            trialCode1.setCode(code);
            trialCode1.setStatus(1);
            trialCode1.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
            trialCodeList.add(trialCode1);
        });

        boolean redisConsume = redisUtil.sRem(TRIAL_CODE_KEY, trialCodeSet.toArray()) > 0;

        TrialCodeStatistics statistics = lowTrialCodeStatistics(trialCodeSet);

        boolean result2 = trialCodeStatisticsDao.update(statistics) > 0;
        boolean update = update(trialCodeList);
        return redisConsume && update && result2;
    }

    //数据减少时更新TrialCodeStatistics
    @NotNull
    private TrialCodeStatistics lowTrialCodeStatistics(Set<String> trialCodeSet) {
        int length = trialCodeSet.stream().toArray().length;
        TrialCodeStatistics statistics = trialCodeStatisticsDao.queryById(1);
        TrialCodeStatistics result = new TrialCodeStatistics();
        long avail = statistics.getAvailableCount() - length;
        long total = statistics.getTotalGenerated() - length;
        result.setAvailableCount(avail);
        result.setTotalGenerated(total);
        result.setUsePercentage((double) avail / total);
        result.setUpdatedTime(new Date());
        return result;
    }


    //随机码去重
    private String generateUniqueCode() {
        String code;
        do {
            code = generateRandomCode();
        } while (redisUtil.sIsMember(TRIAL_CODE_KEY, code) ||
                redisUtil.exist(TRIAL_CODE_USED_KEY)); // 检查是否已在可用集合或已使用集合中
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
