package com.example.demo.controller;

import com.example.demo.entity.Emp;
import com.example.demo.entity.Emp;
import com.example.demo.entity.Menu;
import com.example.demo.entity.response.ResponseListAndMap;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.service.EmpService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by more-time on 2019/6/23.
 */
@RestController
@RequestMapping("/emp")
public class EmpController {
    @Autowired
    private EmpService empService;


    @GetMapping("/findList")
    public ResponsePageResult findList(int page, int limit, Emp emp) {
        return empService.findList(page, limit, emp);
    }

    @GetMapping("/listAndMap")
    public ResponseListAndMap listAndMap() {
        List<Emp> list = empService.list();
        Map map = new HashMap();
        for (Emp emp :
                list) {
            map.put(emp.getUuid(), emp.getName());
        }
        return new ResponseListAndMap(list, map);
    }

//    @RequiresPermissions("员工")
    @PostMapping("/add")
    public ResponseResult add(@RequestBody Emp emp) {
        return empService.add(emp);
    }

    @GetMapping("/findOne")
    public Emp findOne(Long id) {
        return empService.findOne(id);
    }

//    @RequiresPermissions("员工")
    @PostMapping("/update")
    public ResponseResult update(@RequestBody Emp emp) {
        return empService.update(emp);
    }

//    @RequiresPermissions("员工")
    @GetMapping("/del")
    public ResponseResult del(Long id) {
        return empService.del(id);
    }

    @GetMapping("/getMenusByEmpuuid")
    public Menu getMenusByEmpuuid() {
        Subject subject = SecurityUtils.getSubject();
        //提取主角,拿到emp
        Emp emp = (Emp) subject.getPrincipal();
        if (emp == null) {
            return null;
        }
        return empService.readMenusByEmpuuid(emp.getUuid());
    }

//    @RequiresPermissions("重置密码")
    @PostMapping("/resetPwd")
    public ResponseResult resetPwd(@RequestBody Emp emp) {
        return empService.resetPwd(emp);
    }
}
