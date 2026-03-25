package com.suifeng.yquest.service;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.entity.Company;

import java.util.List;

/**
 * (Company)表服务接口
 */
public interface CompanyService {
    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Company queryById(Long id);

    /**
     * 分页查询
     *
     * @param company 筛选条件
     * @return 查询结果
     */
    PageResult<Company> queryByPage(Company company);

    /**
     * 条件查询
     *
     * @param company 筛选条件
     * @return 结果列表
     */
    List<Company> queryAll(Company company);

    /**
     * 新增数据
     *
     * @param company 实例对象
     * @return 实例对象
     */
    boolean insert(Company company);

    /**
     * 修改数据
     *
     * @param company 实例对象
     * @return 实例对象
     */
    boolean update(Company company);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 根据公司名称查询
     *
     * @param companyName 公司名称
     * @return 公司
     */
    Company queryByCompanyName(String companyName);

    /**
     * 审核公司
     *
     * @param id 公司ID
     * @param status 审核状态（1：已通过，0：未通过）
     * @return 是否成功
     */
    boolean auditCompany(Long id, Integer status);

}
