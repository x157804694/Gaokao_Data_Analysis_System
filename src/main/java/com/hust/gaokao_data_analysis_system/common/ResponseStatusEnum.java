package com.hust.gaokao_data_analysis_system.common;

public enum ResponseStatusEnum {
    SUCCESS(200, "请求成功"), FAILED(400, "请求失败"), NOT_FOUND(404, "接口不存在"), SERVER_ERROR(500, "服务器内部出错"), USER_INSUFFICIENT_AUTHORITY(403, "权限不足");

    ResponseStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
