package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 仓库库存实体类
 * @author Administrator *
 */
@ToString
@Data
@Entity
@Table(name = "STOREDETAIL")
public class Storedetail {
	@Id
	@SequenceGenerator(name = "STOREDETAIL_SEQ", sequenceName = "STOREDETAIL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOREDETAIL_SEQ")
	private Long uuid;//编号
	private Long storeuuid;//仓库编号
	private Long goodsuuid;//商品编号
	private Long num;//数量


}
