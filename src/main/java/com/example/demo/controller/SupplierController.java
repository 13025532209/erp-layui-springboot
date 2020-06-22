package com.example.demo.controller;

import com.example.demo.entity.Supplier;
import com.example.demo.entity.response.ResponseListAndMap;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by more-time on 2019/6/23.
 */
@RestController
@RequestMapping("/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;

    @GetMapping("/findList")
    public ResponsePageResult findList(int page, int limit, Supplier supplier) {
        return supplierService.findList(page, limit, supplier);
    }
    @GetMapping("list")
    public List<Supplier> list(){
        return supplierService.list();
    }

    @GetMapping("/listAndMap")
    public ResponseListAndMap listAndMap() {
        List<Supplier> list = supplierService.list();
        Map map = new HashMap();
        for (Supplier supplier :
                list) {
            map.put(supplier.getUuid(), supplier.getName());
        }
        return new ResponseListAndMap(list,map);
    }



    @PostMapping("/add")
    public ResponseResult add(@RequestBody Supplier supplier) {
        return supplierService.add(supplier);
    }

    @GetMapping("/findOne")
    public Supplier findOne(Long id) {
        return supplierService.findOne(id);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody Supplier supplier) {
        return supplierService.update(supplier);
    }

    @GetMapping("/del")
    public ResponseResult del(Long id) {
        return supplierService.del(id);
    }
}
