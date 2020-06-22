package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * 仓库实体类
 * @author Administrator *
 */
@ToString
@Data
@Entity
@Table(name = "STORE")
public class Store {
	@Id
	@SequenceGenerator(name = "STORE_SEQ", sequenceName = "STORE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STORE_SEQ")
	private Long uuid;//编号
	private String name;//名称
	private Long empuuid;//员工编号

	@Transient
	private List<Storedetail> storedetailList;
}
