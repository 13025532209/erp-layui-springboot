package com.example.demo.dao;

import com.example.demo.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by more-time on 2019/6/23.
 */
public interface GoodsDao extends JpaRepository<Goods,Long> {
}
