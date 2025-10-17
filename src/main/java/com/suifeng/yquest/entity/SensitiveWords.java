package com.suifeng.yquest.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.suifeng.yquest.api.common.PageInfo;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * 敏感词表
 */
@Data
@TableName("sensitive_words")
public class SensitiveWords extends PageInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 内容
     */
    private String words;

    /**
     * 1=黑名单 2=白名单
     */
    private Integer type;

    /**
     * 是否被删除 0未删除 1已删除
     */
    private Integer isDeleted;

    @TableField(exist = false)
    private Integer pageNo = 1;

    @TableField(exist = false)
    private Integer pageSize = 10;

    public Integer getPageNo() {
        return (pageNo < 1 || pageNo == null) ? 1 : pageNo;
    }

    public Integer getPageSize() {
        return (pageSize < 1 || pageSize == null || pageSize > Integer.MAX_VALUE) ? 10 : pageSize;
    }
}
