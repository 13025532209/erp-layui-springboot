package com.example.demo.entity.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by more-time on 2019/6/16.
 */
@Data
@ToString
public class ResponsePageResult<T> {
    private long code;
    private String msg;
    private long count;
    private List<T> data;

    public ResponsePageResult(long code, String msg, long count, List<T> data) {
        this.code = code;
        this.msg = msg;
        this.count = count;
        this.data = data;
    }

    public ResponsePageResult(String msg) {
        this.msg = msg;
    }
}
