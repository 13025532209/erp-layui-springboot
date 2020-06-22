package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

/**
 * 角色实体类
 * @author Administrator *
 */
@ToString
@Data
@Entity
@Table(name = "ROLE")
public class Role {
	@Id
	@SequenceGenerator(name = "ROLE_SEQ", sequenceName = "ROLE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ROLE_SEQ")
	private Long uuid;//编号
	private String name;//名称

	@JsonIgnore
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "ROLE_MENU",joinColumns = @JoinColumn(name = "ROLEUUID"),
			inverseJoinColumns = @JoinColumn(name = "MENUUUID"))
	private List<Menu> menus;


}
