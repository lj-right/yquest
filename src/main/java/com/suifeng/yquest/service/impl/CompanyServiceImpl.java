package com.suifeng.yquest.service.impl;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.api.enums.IsDeletedFlagEnum;
import com.suifeng.yquest.entity.Company;
import com.suifeng.yquest.dao.CompanyDao;
import com.suifeng.yquest.service.CompanyService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * (Company)表服务实现类
 */
@Service("companyService")
public class CompanyServiceImpl implements CompanyService {
    @Resource
    private CompanyDao companyDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public Company queryById(Long id) {
        return this.companyDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param company 筛选条件
     * @return 查询结果
     */
    @Override
    public PageResult<Company> queryByPage(Company company) {
        PageResult<Company> pageResult = new PageResult<>();
        pageResult.setPageNo(company.getPageNo());
        pageResult.setPageSize(company.getPageSize());
        int start = (company.getPageNo() - 1) * company.getPageSize();

        int count = companyDao.countByCondition(company);
        if (count == 0) {
            return pageResult;
        }
        List<Company> companyList = companyDao.queryPage(company, start, company.getPageSize());
        pageResult.setRecords(companyList);
        pageResult.setTotal(count);
        return pageResult;
    }

    /**
     * 条件查询
     *
     * @param company 筛选条件
     * @return 结果列表
     */
    @Override
    public List<Company> queryAll(Company company) {
        return companyDao.queryAll(company);
    }

    /**
     * 新增数据
     *
     * @param company 实例对象
     * @return 实例对象
     */
    @Override
    public boolean insert(Company company) {
        // 检查公司名称是否已存在
        Company existingCompany = companyDao.queryByCompanyName(company.getCompanyName());
        if (existingCompany != null) {
            return false;
        }
        
        company.setAuditStatus(0); // 待审核
        company.setCreatedTime(new Date());
        company.setUpdatedTime(new Date());
        company.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        
        return this.companyDao.insert(company) > 0;
    }

    /**
     * 修改数据
     *
     * @param company 实例对象
     * @return 实例对象
     */
    @Override
    public boolean update(Company company) {
        company.setUpdatedTime(new Date());
        return this.companyDao.update(company) > 0;
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.companyDao.deleteById(id) > 0;
    }

    /**
     * 根据公司名称查询
     *
     * @param companyName 公司名称
     * @return 公司
     */
    @Override
    public Company queryByCompanyName(String companyName) {
        return companyDao.queryByCompanyName(companyName);
    }

    /**
     * 审核公司
     *
     * @param id 公司ID
     * @param status 审核状态（1：已通过，0：未通过）
     * @return 是否成功
     */
    @Override
    public boolean auditCompany(Long id, Integer status) {
        Company company = new Company();
        company.setId(id);
        company.setAuditStatus(status);
        company.setUpdatedTime(new Date());
        return companyDao.update(company) > 0;
    }

}
