package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.entity.Refer;
import com.suifeng.yquest.dao.ReferDao;
import com.suifeng.yquest.service.ReferService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (Refer)表服务实现类
 */
@Service("referService")
public class ReferServiceImpl implements ReferService {
    @Resource
    private ReferDao referDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Refer queryById(Long id) {
        return this.referDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param refer 实例对象
     * @return 实例对象
     */
    @Override
    public Refer insert(Refer refer) {
        this.referDao.insert(refer);
        return refer;
    }

    /**
     * 修改数据
     *
     * @param refer 实例对象
     * @return 实例对象
     */
    @Override
    public Refer update(Refer refer) {
        this.referDao.update(refer);
        return this.queryById(refer.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.referDao.deleteById(id) > 0;
    }
}
