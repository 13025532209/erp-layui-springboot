package com.example.demo.service;

import com.example.demo.dao.EmpDao;
import com.example.demo.entity.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by more-time on 2019/6/30.
 */
@Service
public class LoginService {
    @Autowired
    private EmpDao empDao;


    public Emp findEmpByUsername(String username,String pwd){
        return empDao.findEmpByUsernameAndPwd(username,pwd);
    }
}
