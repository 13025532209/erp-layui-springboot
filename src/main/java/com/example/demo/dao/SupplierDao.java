package com.example.demo.dao;

import com.example.demo.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by more-time on 2019/6/25.
 */
public interface SupplierDao extends JpaRepository<Supplier,Long> {
}
