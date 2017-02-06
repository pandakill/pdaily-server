package org.panda.pdaily.util;

/**
 * 接口请求的数据返回代码和状态值
 *
 * Created by fangzanpan on 2017/2/4.
 */
public enum PDHttpStatus {

    SUCCESS(200, "请求成功."),

    FAIL_REQUEST(300, "请求失败."),
    FAIL_REQUEST_UNVALID_PARAMS(301, "参数违法."),
    FAIL_USER_TOKEN_UNVALID(310, "token失效."),
    FAIL_USER_PSW_UNVALID(311, "密码错误."),
    FAIL_USER_UNPERMISSION(312, "用户没有改权限."),
    FAIL_USER_UNKNOWK(314, "找不到该用户."),

    FAIL_APPLICATION_ERROR(500, "程序出错.")
    ;

    public int code;
    public String msg;

    PDHttpStatus(int value, String msg) {
        this.code = value;
        this.msg = msg;
    }
}
