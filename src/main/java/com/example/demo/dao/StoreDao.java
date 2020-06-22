package com.example.demo.dao;

import com.example.demo.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by more-time on 2019/6/24.
 */
public interface StoreDao extends JpaRepository<Store,Long> {
    List<Store> findByEmpuuid(Long empuuid);

}
