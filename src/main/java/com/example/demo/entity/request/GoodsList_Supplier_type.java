package com.example.demo.entity.request;

import com.example.demo.entity.Goods;
import com.example.demo.entity.Orders;
import com.example.demo.entity.Supplier;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by more-time on 2019/6/26.
 */
@ToString
@Data
public class GoodsList_Supplier_type {
    private Supplier supplier;
    private String type;
    private List<GoodsQuvo> goodsList;
    private Double total;
}
