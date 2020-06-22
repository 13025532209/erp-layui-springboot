package com.example.demo.dao;

import com.example.demo.entity.Menu;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * Created by more-time on 2019/6/29.
 */
@Mapper
public interface EmpMapper {
    List<Menu> getMenusByEmpuuid(Long uuid);
}
