package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 供应商实体类
 * @author Administrator *
 */@ToString
@Data
@Entity
@Table(name = "SUPPLIER")
public class Supplier {

	@Id
	@SequenceGenerator(name = "SUPPLIER_SEQ", sequenceName = "SUPPLIER_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SUPPLIER_SEQ")
	private Long uuid;//编号
	private String name;//名称
	private String address;//联系地址
	private String contact;//联系人
	private String tele;//联系电话
	private String email;//邮件地址
	private String type;//1：供应商 2：客户

}
