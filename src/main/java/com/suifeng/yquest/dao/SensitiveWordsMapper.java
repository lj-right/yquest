package com.suifeng.yquest.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.suifeng.yquest.entity.Refer;
import com.suifeng.yquest.entity.SensitiveWords;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 *
 * 敏感词表 Mapper 接口
 */
public interface SensitiveWordsMapper extends BaseMapper<SensitiveWords> {

    int countByCondition(SensitiveWords sw);

    @Select("SELECT * FROM sensitive_words LIMIT #{start}, #{pageSize}")
    List<SensitiveWords> queryAllWords(@Param("start") int start, @Param("pageSize") Integer pageSize);
}
