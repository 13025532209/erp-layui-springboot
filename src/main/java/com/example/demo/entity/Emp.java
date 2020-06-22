package com.example.demo.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * 员工实体类
 *
 * @author Administrator *
 */
@Data
@Entity
@Table(name = "emp")
/*@NamedEntityGraph(name = "Emp.Graph", attributeNodes = {@NamedAttributeNode("roles")})*/
public class Emp {
    @Id
    @SequenceGenerator(name = "EMP_SEQ", sequenceName = "EMP_SEQ", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMP_SEQ")
    private Long uuid;//编号
    private String username;//登陆名
    //不转换json字符串
    @JsonIgnore
    private String pwd;//登陆密码

    private String name;//真实姓名
    private Long gender;//性别
    private String email;//邮件地址
    private String tele;//联系电话
    private String address;//联系地址
    @Transient
    private String state;
    @Temporal(TemporalType.DATE)
    //2019-06-05
    private Date birthday;//出生年月日
    @Transient
    private String birthday1;//出生年月日
    @Transient
    private String birthday2;//出生年月日
    private Long depuuid;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "EMP_ROLE",joinColumns = @JoinColumn(name = "EMPUUID"),
            inverseJoinColumns = @JoinColumn(name = "ROLEUUID"))
    private List<Role> roles;//用户下所拥有的角色集合
}
