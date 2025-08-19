package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.entity.ShareMessage;
import com.suifeng.yquest.dao.ShareMessageDao;
import com.suifeng.yquest.service.ShareMessageService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

/**
 * 信息提醒表(ShareMessage)表服务实现类
 */
@Service("shareMessageService")
public class ShareMessageServiceImpl implements ShareMessageService {
    @Resource
    private ShareMessageDao shareMessageDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ShareMessage queryById(Long id) {
        return this.shareMessageDao.queryById(id);
    }


    /**
     * 新增数据
     *
     * @param shareMessage 实例对象
     * @return 实例对象
     */
    @Override
    public ShareMessage insert(ShareMessage shareMessage) {
        this.shareMessageDao.insert(shareMessage);
        return shareMessage;
    }

    /**
     * 修改数据
     *
     * @param shareMessage 实例对象
     * @return 实例对象
     */
    @Override
    public ShareMessage update(ShareMessage shareMessage) {
        this.shareMessageDao.update(shareMessage);
        return this.queryById(shareMessage.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.shareMessageDao.deleteById(id) > 0;
    }
}
