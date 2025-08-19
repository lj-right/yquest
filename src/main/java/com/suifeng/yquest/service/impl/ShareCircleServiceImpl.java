package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.entity.ShareCircle;
import com.suifeng.yquest.dao.ShareCircleDao;
import com.suifeng.yquest.service.ShareCircleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 圈子表(ShareCircle)表服务实现类
 */
@Service("shareCircleService")
public class ShareCircleServiceImpl implements ShareCircleService {
    @Resource
    private ShareCircleDao shareCircleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ShareCircle queryById(Long id) {
        return this.shareCircleDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param shareCircle 实例对象
     * @return 实例对象
     */
    @Override
    public ShareCircle insert(ShareCircle shareCircle) {
        this.shareCircleDao.insert(shareCircle);
        return shareCircle;
    }

    /**
     * 修改数据
     *
     * @param shareCircle 实例对象
     * @return 实例对象
     */
    @Override
    public ShareCircle update(ShareCircle shareCircle) {
        this.shareCircleDao.update(shareCircle);
        return this.queryById(shareCircle.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.shareCircleDao.deleteById(id) > 0;
    }
}
