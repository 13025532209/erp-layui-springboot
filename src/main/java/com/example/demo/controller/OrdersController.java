package com.example.demo.controller;

import com.example.demo.entity.Orders;
import com.example.demo.entity.request.GoodsList_Supplier_type;
import com.example.demo.entity.response.OrdersQuvo;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by more-time on 2019/6/23.
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @GetMapping("/findList")
    public ResponsePageResult findList(int page, int limit, Orders orders) {
        return ordersService.findList(page, limit, orders);
    }

    @GetMapping("/updateState")
    public ResponseResult updateState(Long id,String state){
        return ordersService.updateState(id,state);
    }
    @PostMapping("/add")
    public ResponseResult add(@RequestBody GoodsList_Supplier_type more) {
        return ordersService.add(more);
    }

    @GetMapping("/findOne")
    public OrdersQuvo findOne(Long id) {
        return ordersService.findOne(id);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody Orders orders) {
        return ordersService.update(orders);
    }

    @GetMapping("/del")
    public ResponseResult del(Long id) {
        return ordersService.del(id);
    }
}
