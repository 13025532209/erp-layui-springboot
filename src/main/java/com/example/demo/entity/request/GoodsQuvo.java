package com.example.demo.entity.request;

import com.example.demo.entity.Goods;
import lombok.Data;
import lombok.ToString;

/**
 * Created by more-time on 2019/6/26.
 */
@ToString
@Data
public class GoodsQuvo {
   private Goods goods;
   private Double money;
   private Long num;
}
