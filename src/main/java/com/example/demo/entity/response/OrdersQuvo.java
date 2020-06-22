package com.example.demo.entity.response;

import com.example.demo.entity.Orderdetail;
import com.example.demo.entity.Orders;
import lombok.Data;
import lombok.ToString;

import java.util.List;

/**
 * Created by more-time on 2019/6/25.
 */
@ToString
@Data
public class OrdersQuvo {
    private Orders orders;
    private List<Orderdetail> orderdetailList;
}
