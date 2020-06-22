package com.example.demo.dao;

import com.example.demo.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * Created by more-time on 2019/6/25.
 */
public interface OrdersDao extends JpaRepository<Orders,Long> ,JpaSpecificationExecutor<Orders> {
}
