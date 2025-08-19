package com.suifeng.yquest.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 敏感词表(SensitiveWords)实体类
 */
@Data
public class SensitiveWords implements Serializable {
    private static final long serialVersionUID = 939212038043006154L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 单词
     */
    private String words;
    /**
     * 类型
     */
    private Integer type;
    /**
     * 是否删除
     */
    private Integer isDeleted;
}

