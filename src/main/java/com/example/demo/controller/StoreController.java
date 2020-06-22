package com.example.demo.controller;

import com.example.demo.entity.Store;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.entity.response.StoreGoods;
import com.example.demo.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by more-time on 2019/6/23.
 */
@RestController
@RequestMapping("/store")
public class StoreController {
    @Autowired
    private StoreService storeService;

    @GetMapping("/findList")
    public ResponsePageResult findList(int page, int limit, Store store) {
        return storeService.findList(page, limit, store);
    }
    @GetMapping("/getStoreByEmpuuid")
    public List<Store> getStoreByEmpuuid(){
        return storeService.getStoreByEmpuuid((long)2);
    }
    @PostMapping("/add")
    public ResponseResult add(@RequestBody Store store) {
        return storeService.add(store);
    }

    @GetMapping("/findOne")
    public Store findOne(Long id) {
        return storeService.findOne(id);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody Store storetype) {
        return storeService.update(storetype);
    }

    @GetMapping("/del")
    public ResponseResult del(Long id) {
        return storeService.del(id);
    }

    @GetMapping("/getStoreGoodsById")
    public ResponsePageResult<StoreGoods> getStoreGoodsById(Long id){

        return new ResponsePageResult<>(0,"操作成功",0,storeService.getStoreGoodsById(id));
    }
}
