package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.EnableMBeanExport;

import javax.persistence.*;

/**
 * 部门
 */
@ToString
@Data
@Entity
@Table(name = "dep")
public class Dep {
    @Id
    @SequenceGenerator(name = "DEP_SEQ", sequenceName = "DEP_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEP_SEQ")

    /** 部门编号 **/
    private Long uuid;

    /**
     * 部门名称
     */
    private String name;

    /**
     * 联系电话
     */
    private String tele;
}
