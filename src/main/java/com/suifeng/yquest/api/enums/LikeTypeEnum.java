package com.suifeng.yquest.api.enums;


public enum LikeTypeEnum {

    MOMENT(0, "动态点赞"),
    COMMENT(1, "评论点赞"),
    REPLY(2, "回复点赞");

    private int code;
    private String desc;

    LikeTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public static LikeTypeEnum getByCode(int codeVal) {
        for (LikeTypeEnum likeTypeEnum : LikeTypeEnum.values()) {
            if (codeVal == likeTypeEnum.code) {
                return likeTypeEnum;
            }
        }
        return null;
    }

}
