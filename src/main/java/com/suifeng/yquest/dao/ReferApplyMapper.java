package com.suifeng.yquest.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suifeng.yquest.entity.ReferApply;
import org.apache.ibatis.annotations.Param;

/**
 * 内推申请 Mapper 接口
 */
public interface ReferApplyMapper extends BaseMapper<ReferApply> {

    /**
     * 条件更新当前状态（乐观锁：仅当处于原状态时才流转，防止并发重复流转）
     *
     * @param id           申请ID
     * @param fromStatus   原状态
     * @param toStatus     目标状态
     * @param rejectReason 拒绝原因（可为空）
     * @return 影响行数
     */
    int casUpdateStatus(@Param("id") Long id,
                        @Param("fromStatus") Integer fromStatus,
                        @Param("toStatus") Integer toStatus,
                        @Param("rejectReason") String rejectReason);
}
