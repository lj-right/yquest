package com.suifeng.yquest.service.impl;


import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.dao.SensitiveWordsMapper;
import com.suifeng.yquest.entity.Refer;
import com.suifeng.yquest.entity.SensitiveWords;
import com.suifeng.yquest.service.SensitiveWordsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 *
 * 敏感词表 服务实现类
 */
@Service
public class SensitiveWordsServiceImpl extends ServiceImpl<SensitiveWordsMapper, SensitiveWords> implements SensitiveWordsService {

    @Resource
    private SensitiveWordsMapper swDao;

    @Override
    public PageResult<SensitiveWords> queryPage(SensitiveWords sw) {
        PageResult<SensitiveWords> pageResult = new PageResult<>();
        pageResult.setPageNo(sw.getPageNo());
        pageResult.setPageSize(sw.getPageSize());
        int start = (sw.getPageNo() - 1) * sw.getPageSize();

        int count = swDao.countByCondition(sw);
        if(count == 0){
            return pageResult;
        }
        List<SensitiveWords> swList =  swDao.queryAllWords(start,sw.getPageSize());
        pageResult.setRecords(swList);
        pageResult.setTotal(count);
        return pageResult;
    }
}
