package com.suifeng.yquest.api.req;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 *
 * 圈子信息
 *
 */
@Getter
@Setter
public class SaveShareCircleReq implements Serializable {

    /**
     * 圈子名称
     */
    private String circleName;

    /**
     * 圈子图标
     */
    private String icon;

    /**
     * -1为一级,分类ID
     */
    private Long parentId = -1L;

}
