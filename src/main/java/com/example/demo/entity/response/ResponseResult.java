package com.example.demo.entity.response;

import lombok.Data;
import lombok.ToString;

/**
 * Created by more-time on 2019/6/16.
 */
@ToString
@Data
public class ResponseResult<T> {
    private boolean success;
    private String message;
    private T t;
    public ResponseResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ResponseResult(boolean success, String message, T t) {
        this.success = success;
        this.message = message;
        this.t = t;
    }
}
