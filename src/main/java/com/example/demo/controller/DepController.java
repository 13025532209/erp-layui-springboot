package com.example.demo.controller;

import com.example.demo.entity.Dep;
import com.example.demo.entity.response.ResponseListAndMap;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.service.DepService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by more-time on 2019/6/23.
 */
@RestController
@RequestMapping("/dep")
public class DepController {
    @Autowired
    private DepService depService;

    @GetMapping("/findList")
    public ResponsePageResult findList(int page, int limit, Dep dep) {
        return depService.findList(page, limit, dep);
    }

//    @RequiresPermissions(value = {"部门", "重置密码"}, logical = Logical.OR)
    @GetMapping("/listAndMap")
    public ResponseListAndMap listAndMap() {
        List<Dep> list = depService.list();
        Map map = new HashMap();
        for (Dep dep :
                list) {
            map.put(dep.getUuid(), dep.getName());
        }
        return new ResponseListAndMap(list, map);
    }


    @PostMapping("/add")
    public ResponseResult add(@RequestBody Dep dep) {
        return depService.add(dep);
    }

    @GetMapping("/findOne")
    public Dep findOne(Long id) {
        return depService.findOne(id);
    }

    @PostMapping("/update")
    public ResponseResult update(@RequestBody Dep dep) {
        return depService.update(dep);
    }

    @GetMapping("/del")
    public ResponseResult del(Long id) {
        return depService.del(id);
    }
}
