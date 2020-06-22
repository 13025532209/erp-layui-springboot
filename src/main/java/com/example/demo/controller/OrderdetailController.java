package com.example.demo.controller;

import com.example.demo.entity.Orders;
import com.example.demo.entity.request.GoodsList_Supplier_type;
import com.example.demo.entity.response.OrdersQuvo;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.service.OrderdetailService;
import com.example.demo.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by more-time on 2019/6/23.
 */
@RestController
@RequestMapping("/orderdetail")
public class OrderdetailController {
    @Autowired
    private OrderdetailService orderdetailService;

    @GetMapping("/doInStore")
    public ResponseResult doInStore(Long uuid, Long storeuuid){
       return orderdetailService.doInStore(uuid,storeuuid,(long)2);
    }

    @GetMapping("/doOutStore")
    public ResponseResult doOutStore(Long uuid, Long storeuuid){
        return orderdetailService.doOutStore(uuid,storeuuid,(long)2);
    }
}
