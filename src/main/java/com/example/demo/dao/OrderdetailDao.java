package com.example.demo.dao;

import com.example.demo.entity.Orderdetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by more-time on 2019/6/25.
 */
public interface OrderdetailDao  extends JpaRepository<Orderdetail,Long>{
    List<Orderdetail> findByOrdersuuid(Long uuid);

    List<Orderdetail> findByOrdersuuidAndState(Long uuid,String state);
}
