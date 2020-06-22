package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 商品实体类
 * @author Administrator *
 */

@ToString
@Data
@Entity
@Table(name = "GOODS")
public class Goods {
	@Id
	@SequenceGenerator(name = "GOODS_SEQ", sequenceName = "GOODS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODS_SEQ")
	private Long uuid;//编号
	private String name;//名称
	private String origin;//产地
	private String producer;//厂家
	private String unit;//计量单位
	private Double inprice;//进货价格
	private Double outprice;//销售价格



	private Long goodstypeuuid;


}
