package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.entity.SensitiveWords;
import com.suifeng.yquest.dao.SensitiveWordsDao;
import com.suifeng.yquest.service.SensitiveWordsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 敏感词表(SensitiveWords)表服务实现类
 */
@Service("sensitiveWordsService")
public class SensitiveWordsServiceImpl implements SensitiveWordsService {
    @Resource
    private SensitiveWordsDao sensitiveWordsDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SensitiveWords queryById(Long id) {
        return this.sensitiveWordsDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param sensitiveWords 实例对象
     * @return 实例对象
     */
    @Override
    public SensitiveWords insert(SensitiveWords sensitiveWords) {
        this.sensitiveWordsDao.insert(sensitiveWords);
        return sensitiveWords;
    }

    /**
     * 修改数据
     *
     * @param sensitiveWords 实例对象
     * @return 实例对象
     */
    @Override
    public SensitiveWords update(SensitiveWords sensitiveWords) {
        this.sensitiveWordsDao.update(sensitiveWords);
        return this.queryById(sensitiveWords.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.sensitiveWordsDao.deleteById(id) > 0;
    }
}
