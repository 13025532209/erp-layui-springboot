package com.example.demo.dao;

import com.example.demo.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by more-time on 2019/6/28.
 */
public interface MenuDao extends JpaRepository<Menu,String> {
}
