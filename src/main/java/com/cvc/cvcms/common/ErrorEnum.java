package com.cvc.cvcms.common;

/**
 * @author ZZJ
 * @date 2021/3/24 14:55
 * @desc 错误枚举类
 */
public enum ErrorEnum {
    /**
     *错误枚举
     */
    SUCCESS(200,""),
    NO_PERNISSION(403,"没有权限"),
    NO_AUTH(401,"未登录"),
    NOT_FOUND(404,"未找到资源"),
    INNTERNAL_SERVER_ERROR(500,"服务器异常"),
    NOT_ACCEPTABLE(406,"无法根据请求的内容特性完成请求");

    private Integer status;
    private String error;

    ErrorEnum(Integer status, String error) {
        this.error = error;
        this.status = status;
    }

    public Integer getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }
}
