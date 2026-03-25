package com.suifeng.yquest.dao;

import com.suifeng.yquest.entity.Company;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 公司表(Company)数据库访问层
 */
@Mapper
public interface CompanyDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    Company queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param offset 查询起始位置
     * @param limit  查询条数
     * @return 对象列表
     */
    List<Company> queryAllByLimit(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 条件查询
     *
     * @param company 查询条件
     * @return 对象列表
     */
    List<Company> queryAll(@Param("company") Company company);

    /**
     * 分页查询
     *
     * @param company 查询条件
     * @param start 起始位置
     * @param pageSize 每页大小
     * @return 分页数据
     */
    List<Company> queryPage(@Param("company") Company company, @Param("start") int start, @Param("pageSize") Integer pageSize);

    /**
     * 计算总数
     *
     * @param company 查询条件
     * @return 总数
     */
    int countByCondition(@Param("company") Company company);

    /**
     * 新增数据
     *
     * @param company 实例对象
     * @return 影响行数
     */
    int insert(Company company);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<Company> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<Company> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<Company> 实例对象列表
     * @return 影响行数
     */
    int insertOrUpdateBatch(@Param("entities") List<Company> entities);

    /**
     * 修改数据
     *
     * @param company 实例对象
     * @return 影响行数
     */
    int update(Company company);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

    /**
     * 根据公司名称查询
     *
     * @param companyName 公司名称
     * @return 公司
     */
    Company queryByCompanyName(String companyName);

}
