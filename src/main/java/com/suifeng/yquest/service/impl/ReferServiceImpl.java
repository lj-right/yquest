package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.Refer;
import com.suifeng.yquest.dao.ReferDao;
import com.suifeng.yquest.service.ReferService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

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
        refer.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode()); //用于审核
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
    public Integer update(Refer refer) {
        return this.referDao.update(refer);
    }

    /**
     * 通过主键删除数据
     *
     * @param refer 实例对象
     * @return 实例对象
     */
    @Override
    public Integer deleteById(Refer refer) {
        return this.referDao.deleteById(refer.getId());
    }

    @Override
    public PageResult<Refer> queryReferPage(Refer refer) {
        PageResult<Refer> pageResult = new PageResult<>();
        pageResult.setPageNo(refer.getPageNo());
        pageResult.setPageSize(refer.getPageSize());
        int start = (refer.getPageNo() - 1) * refer.getPageSize();

        int count = referDao.countByCondition(refer);
        if(count == 0){
            return pageResult;
        }
        List<Refer> referList =  referDao.queryAllRefer(start,refer.getPageSize());
        pageResult.setRecords(referList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 用于后台审核新内推内容
     * @param refer
     * @return
     */
    @Override
    public PageResult<Refer> manageReferPage(Refer refer) {
        PageResult<Refer> pageResult = new PageResult<>();
        pageResult.setPageNo(refer.getPageNo());
        pageResult.setPageSize(refer.getPageSize());
        int start = (refer.getPageNo() - 1) * refer.getPageSize();

        int count = referDao.countByDelCondition(refer);
        if(count == 0){
            return pageResult;
        }
        List<Refer> referList =  referDao.queryAllDelRefer(start,refer.getPageSize());
        pageResult.setRecords(referList);
        pageResult.setTotal(count);
        return pageResult;
    }

    @Override
    public PageResult<Refer> searchByMessage(Refer refer) {
        refer.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        PageResult<Refer> pageResult = new PageResult<>();
        pageResult.setPageNo(refer.getPageNo());
        pageResult.setPageSize(refer.getPageSize());
        int start = (refer.getPageNo() - 1) * refer.getPageSize();

        int count = referDao.countBySearch(refer);
        if(count == 0){
            return pageResult;
        }
        List<Refer> referList =  referDao.searchByMessage(start,refer.getPageSize(),refer.getCompany());
        pageResult.setRecords(referList);
        pageResult.setTotal(count);
        return pageResult;
    }
}
