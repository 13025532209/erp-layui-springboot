package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 仓库操作记录实体类
 * @author Administrator *
 */
@ToString
@Data
@Entity
@Table(name = "STOREOPER")
public class Storeoper {
	/**1：入库*/
	public static final String TYPE_IN = "1";
	/** 2：出库*/
	public static final String TYPE_OUT = "2";

	@Id
	@SequenceGenerator(name = "STOREOPER_SEQ", sequenceName = "STOREOPER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STOREOPER_SEQ")
	private Long uuid;//编号
	private Long empuuid;//操作员工编号
	private java.util.Date opertime;//操作日期
	private Long storeuuid;//仓库编号
	private Long goodsuuid;//商品编号
	private Long num;//数量
	private String type;//1：入库 2：出库


}
