package com.suifeng.yquest.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suifeng.yquest.entity.ShareMoment;
import org.springframework.data.repository.query.Param;

/**
 *
 * 动态信息 Mapper 接口
 */
public interface ShareMomentMapper extends BaseMapper<ShareMoment> {

    void incrReplyCount(@Param("id") Long id, @Param("count") int count);

}
