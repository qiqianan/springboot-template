package com.west2.common;

/**
 * @author 1
 * 返回结果枚举
 */

public enum ResponeEnum {
    /**
     * 操作成功
     */
    SUCCSEE(200,"操作成功"),

    /**
     * 操作失败
     */
    ERROR(500,"操作失败"),

    /**
     * 登录失败
     */
    LOGIN_ERROR(500210,"登录失败");


    /**
     * 构造方法
     * @param code
     * @param msg
     */
    ResponeEnum(Integer code,String msg){
        this.code = code;
        this.msg = msg;
    }

    private Integer code;
    private String msg;

    public Integer getCode(){
        return code;
    }

    public String getMsg(){
        return msg;
    }

}
