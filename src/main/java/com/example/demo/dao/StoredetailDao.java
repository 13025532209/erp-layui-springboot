package com.example.demo.dao;

import com.example.demo.entity.Storedetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by more-time on 2019/6/27.
 */
public interface StoredetailDao extends JpaRepository<Storedetail,Long> {
    Storedetail findByGoodsuuidAndStoreuuid(Long goodsuuid,Long storeuuid);

    List<Storedetail> findByStoreuuid(Long storeuuid);
}
