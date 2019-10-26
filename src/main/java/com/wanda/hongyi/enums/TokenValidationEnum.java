package com.wanda.hongyi.enums;

/**
 * @Author 知非
 * @Email wanda92632@163.com
 * @Date 2019/10/23 18:45
 */
public enum TokenValidationEnum {
    /**不合法的token**/
    NO_TRUST("签名不合法的token，已拦截请求", "110"),
    /**token已过期**/
    EXPIRE("该用户的token已过期，需重新登录", "111"),
    /**未知问题**/
    OTHER_ERRORE("用户token验证出现未知问题，已拦截请求", "112");

    private String errorCode;
    private String desc;

    TokenValidationEnum(String desc, String code) {
        this.errorCode = code;
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }
}