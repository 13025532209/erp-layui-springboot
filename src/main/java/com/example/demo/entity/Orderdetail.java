package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;

/**
 * 订单明细实体类
 * @author Administrator *
 */

@ToString
@Data
@Entity
@Table(name = "ORDERDETAIL")
public class Orderdetail {
	/**未入库 */
	public static final String STATE_NOT_IN = "0";
	/**已入库*/
	public static final String STATE_IN = "1";

	/**未出库 */
	public static final String STATE_NOT_OUT = "0";
	/**已出库*/
	public static final String STATE_OUT = "1";
	@Id
	@SequenceGenerator(name = "ORDERDETAIL_SEQ", sequenceName = "ORDERDETAIL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERDETAIL_SEQ")
	private Long uuid;//编号
	private Long goodsuuid;//商品编号
	private String goodsname;//商品名称
	private Double price;//价格
	private Long num;//数量
	private Double money;//金额
	private java.util.Date endtime;//结束日期
	private Long ender;//库管员
	private Long storeuuid;//仓库编号
	private String state;//采购：0=未入库，1=已入库  销售：0=未出库，1=已出库
	private Long ordersuuid;//订单编号


}
