package com.suifeng.yquest.api.enums;

public enum LoginInfoTypeEnum {
    LOGIN_BY_NAME(1,"用户名登录"),
    LOGIN_BY_EMAIL(2,"邮箱登录");

    private int code;
    private String desc;

    LoginInfoTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static LoginInfoTypeEnum getByCode(int codeVal) {
        for (LoginInfoTypeEnum resultCodeEnum : LoginInfoTypeEnum.values()) {
            if (resultCodeEnum.code == codeVal) {
                return resultCodeEnum;
            }
        }
        return null;
    }
}
