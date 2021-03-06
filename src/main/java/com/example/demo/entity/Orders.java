package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * 订单实体类
 * @author Administrator *
 */
@ToString
@Data
@Entity
@Table(name = "orders")
public class Orders {
	/** 未审核 */
	public static final String STATE_CREATE = "0";
	/** 已审核 */
	public static final String STATE_CHECK = "1";
	/** 已确认 */
	public static final String STATE_START = "2";
	/** 已入库 */
	public static final String STATE_END = "3";

	/** 未出库 */
	public static final String STATE_NOT_OUT = "0";
	/** 已出库 */
	public static final String STATE_OUT = "1";

	/** 采购订单 */
	public static final String TYPE_IN = "1";
	/** 销售订单 */
	public static final String TYPE_OUT = "2";


	@Id
	@SequenceGenerator(name = "ORDERS_SEQ", sequenceName = "ORDERS_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ")
	private Long uuid;//编号

	private Date createtime;//生成日期
	private Date checktime;//审核日期
	private Date starttime;//确认日期
	/*@Temporal(TemporalType.DATE)*/
	private Date endtime;//入库或出库日期
	private String type;//1:采购 2:销售
	private Long creater;//下单员
	private Long checker;//审核员
	private Long starter;//采购员
	private Long ender;//库管员
	private Long supplieruuid;//供应商或客户
	private Double totalmoney;//合计金额
	private String state;//采购: 0:未审核 1:已审核, 2:已确认, 3:已入库；销售：0:未出库 1:已出库
	private Long waybillsn;//运单号
}
