package com.suifeng.yquest.service;

import com.suifeng.yquest.api.common.PageResult;
import com.suifeng.yquest.entity.TrialCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Set;

/**
 * (TrialCode)表服务接口
 */
public interface TrialCodeService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    TrialCode queryById(Integer id);

    /**
     * 分页查询
     *
     * @param trialCode   筛选条件
     * @return 查询结果
     */
    PageResult<TrialCode> queryByPage(TrialCode trialCode);

    /**
     * 新增数据
     *
     * @param trialCode 实例对象
     * @return 实例对象
     */
    Boolean insert(TrialCode trialCode);

    /**
     * 修改数据
     *
     * @param trialCode 实例对象
     * @return 实例对象
     */
    boolean update(Set<TrialCode> trialCode);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

    /**
     * 批量插入数据
     * @param trialCodeList
     * @return
     */
    boolean insertBatch(Set<TrialCode> trialCodeList);

    /**
     * 生成唯一试用码
     * @return
     */
    boolean generateTrialCodeBatch();

    boolean cleanExpiredCodes();

    boolean ConsumeTrialCode(Set<String> trialCode);
}
