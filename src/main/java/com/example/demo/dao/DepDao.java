package com.example.demo.dao;

import com.example.demo.entity.Dep;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by more-time on 2019/6/24.
 */
public interface DepDao extends JpaRepository<Dep,Long> {
}
