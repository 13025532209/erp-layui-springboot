package com.example.demo.controller;

import com.example.demo.entity.Goodstype;
import com.example.demo.entity.response.ResponseListAndMap;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.service.GoodsTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by more-time on 2019/6/23.
 */
@RestController
@RequestMapping("/goodsType")
public class GoodsTypeController {
    @Autowired
    private GoodsTypeService goodsTypeService;

    @GetMapping("/findList")
    public ResponsePageResult findList(int page, int limit, Goodstype goodsType) {
        return goodsTypeService.findList(page, limit, goodsType);
    }

    @GetMapping("/listAndMap")
    public ResponseListAndMap listAndMap() {
        List<Goodstype> list = goodsTypeService.list();
        Map map = new HashMap();
        for (Goodstype goodstype :
                list) {
            map.put(goodstype.getUuid(), goodstype.getName());
        }
        return new ResponseListAndMap(list,map);
    }



    @PostMapping("/add")
    public ResponseResult add(@RequestBody Goodstype goodstype) {
        return goodsTypeService.add(goodstype);
    }

    @GetMapping("/findOne")
    public Goodstype findOne(Long id) {
        return goodsTypeService.findOne(id);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody Goodstype goodstype) {
        return goodsTypeService.update(goodstype);
    }

    @GetMapping("/del")
    public ResponseResult del(Long id) {
        return goodsTypeService.del(id);
    }
}
