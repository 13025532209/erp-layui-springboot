package com.example.demo.dao;

import com.example.demo.entity.Emp;
import com.example.demo.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by more-time on 2019/6/28.
 */
public interface RoleDao extends JpaRepository<Role,Long> {


}
