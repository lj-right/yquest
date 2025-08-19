package com.suifeng.yquest.api.common;

import com.suifeng.yquest.api.enums.ResultCodeEnum;
import lombok.Data;

@Data
public class Result <T>{

    private Boolean success;

    private Integer code;

    private String message;

    private T date;

    public static Result ok (){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getDesc());
        return result;
    }
    public static <T> Result ok (T date){
        Result result = new Result();
        result.setSuccess(true);
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMessage(ResultCodeEnum.SUCCESS.getDesc());
        result.setDate(date);
        return result;
    }
    public static Result fail (){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMessage(ResultCodeEnum.FAIL.getDesc());
        return result;
    }
    public static <T> Result fail (T date){
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMessage(ResultCodeEnum.FAIL.getDesc());
        result.setDate(date);
        return result;
    }
}
