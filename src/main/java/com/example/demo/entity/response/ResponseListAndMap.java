package com.example.demo.entity.response;

import lombok.Data;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * Created by more-time on 2019/6/24.
 */

@ToString
@Data
public class ResponseListAndMap {
    private List list;
    private Map map;


    public ResponseListAndMap(List list, Map map) {
        this.list = list;
        this.map = map;
    }
}
