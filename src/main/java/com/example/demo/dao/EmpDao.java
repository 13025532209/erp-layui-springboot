package com.example.demo.dao;

import com.example.demo.entity.Emp;
import com.example.demo.entity.Menu;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Created by more-time on 2019/6/24.
 */
public interface EmpDao extends JpaRepository<Emp, Long>, JpaSpecificationExecutor<Emp> {
    @Query(value = "select menu.* from menu where menuid in(select menuuuid from ROLE_MENU where roleuuid in(select roleuuid from emp_role where empuuid=?1))", nativeQuery = true)
    List<Menu> getMenusByEmpuuid(Long uuid);


    Emp findEmpByUsernameAndPwd(String username,String pwd);

    Emp findEmpByUsername(String username);
}
