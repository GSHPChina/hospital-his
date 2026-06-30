package com.his.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAIL(500, "操作失败"),
    UNAUTHORIZED(401, "未登录或token已过期"),
    FORBIDDEN(403, "没有权限"),
    NOT_FOUND(404, "资源不存在"),
    PARAM_ERROR(400, "参数错误");

    private final int code;
    private final String message;
}
