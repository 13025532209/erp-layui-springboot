package com.example.demo.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.util.List;

/**
 * 菜单实体类
 *
 * @author Administrator *
 */
@Data
@ToString
@Entity
@Table(name = "menu")

public class Menu {
    @Id
    private String menuid;//菜单ID
    private String menuname;//菜单名称
    private String icon;//图标
    private String url;//URL
    @OneToMany(cascade=CascadeType.ALL)
    @JoinColumn(name="pid")
    @OrderBy("MENUID ASC")
    private List<Menu> menus;


    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;//地址相等
        }

        if(obj == null){
            return false;//非空性：对于任意非空引用x，x.equals(null)应该返回false。
        }

        if(obj instanceof Menu){
            Menu other = (Menu) obj;
            //需要比较的字段相等，则这两个对象相等
            if(equalsStr(this.menuname, other.menuname)
                    && equalsStr(this.url, other.url)){
                return true;
            }
        }

        return false;
    }

    private boolean equalsStr(String str1, String str2){
        if(StringUtils.isEmpty(str1) && StringUtils.isEmpty(str2)){
            return true;
        }
        if(!StringUtils.isEmpty(str1) && str1.equals(str2)){
            return true;
        }
        return false;
    }

}
