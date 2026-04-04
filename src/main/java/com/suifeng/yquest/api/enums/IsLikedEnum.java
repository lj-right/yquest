package com.suifeng.yquest.api.enums;


public enum IsLikedEnum {

    LIKE(1, "喜欢"),
    UN_LIKE(0, "不喜欢");

    private int code;
    private String desc;

    IsLikedEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static IsLikedEnum getByCode(int codeVal) {
        for (IsLikedEnum likeTypeEnum : IsLikedEnum.values()) {
            if (codeVal == likeTypeEnum.code) {
                return likeTypeEnum;
            }
        }
        return null;
    }
}
