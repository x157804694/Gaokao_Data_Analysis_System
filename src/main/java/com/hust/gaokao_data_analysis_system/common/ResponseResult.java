package com.hust.gaokao_data_analysis_system.common;

import lombok.Data;

@Data
public class ResponseResult {
    private int code;
    private String message;
    private Object data;

    public ResponseResult(ResponseStatusEnum responseStatusEnum) {
        this.code = responseStatusEnum.getCode();
        this.message = responseStatusEnum.getMessage();
    }

    public static ResponseResult SUCCESS() {
        return new ResponseResult(ResponseStatusEnum.SUCCESS);
    }

    public static ResponseResult FAILED() {
        return new ResponseResult(ResponseStatusEnum.FAILED);
    }

    public static ResponseResult FAILED(String message) {
        ResponseResult responseResult = new ResponseResult(ResponseStatusEnum.FAILED);
        responseResult.setMessage(message);
        return responseResult;
    }

    public ResponseResult setData(Object data) {
        this.data = data;
        return this;
    }
    public ResponseResult setMessage(String message) {
        this.message = message;
        return this;
    }
}
