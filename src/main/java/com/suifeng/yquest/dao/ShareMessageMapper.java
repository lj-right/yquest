package com.suifeng.yquest.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suifeng.yquest.entity.ShareMessage;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * 消息表 Mapper 接口
 */
@Mapper
public interface ShareMessageMapper extends BaseMapper<ShareMessage> {

    void updateReaded(@Param("ids") List<Long> ids);
}
