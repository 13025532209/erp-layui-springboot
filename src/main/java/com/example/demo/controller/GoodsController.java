package com.example.demo.controller;

import com.example.demo.entity.Goods;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by more-time on 2019/6/23.
 */
@RestController
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/findList")
    public ResponsePageResult findList(int page, int limit, Goods goods) {
        return goodsService.findList(page, limit, goods);
    }

    @GetMapping("/list")
    public List<Goods> list(){
        return goodsService.list();
    }
    @PostMapping("/add")
    public ResponseResult add(@RequestBody Goods goods) {
        return goodsService.add(goods);
    }

    @GetMapping("/findOne")
    public Goods findOne(Long id) {
        return goodsService.findOne(id);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody Goods goodstype) {
        return goodsService.update(goodstype);
    }

    @GetMapping("/del")
    public ResponseResult del(Long id) {
        return goodsService.del(id);
    }
}
