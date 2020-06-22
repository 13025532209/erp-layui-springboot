package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 商品分类实体类
 * @author Administrator *
 */


@ToString
@Data
@Entity
@Table(name = "GOODSTYPE")
public class Goodstype {
	@Id
	@SequenceGenerator(name = "GOODSTYPE_SEQ", sequenceName = "GOODSTYPE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "GOODSTYPE_SEQ")
	private Long uuid;//商品类型编号
	private String name;//商品类型名称

}
