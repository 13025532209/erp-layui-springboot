package com.example.demo.controller;

import com.example.demo.entity.Emp;
import com.example.demo.entity.Role;
import com.example.demo.entity.Role;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.entity.response.Tree;
import com.example.demo.service.RoleService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by more-time on 2019/6/28.
 */
@RestController
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;


    @GetMapping("/readRoleMenus")
    public List<Tree> readRoleMenus(Long uuid) {

        return roleService.readRoleMenus(uuid);
    }


    @GetMapping("/findList")
    public ResponsePageResult findList(int page, int limit, Role role) {
        return roleService.findList(page, limit, role);
    }

//    @RequiresPermissions("用户角色设置")
    @GetMapping("/readEmpRoles")
    public List<Tree> readEmpRoles(Long empuuid) {
        return roleService.readEmpRoles(empuuid);
    }

//    @RequiresPermissions("角色权限设置")
    @PostMapping("/updateRoleMenus")
    public ResponseResult updateRoleMenus(Long uuid, @RequestBody List<Tree> treeList) {
        return roleService.updateRoleMenus(uuid, treeList);
    }
//    @RequiresPermissions("用户角色设置")
    @PostMapping("/updateEmpRoles")
    public ResponseResult updateEmpRoles(Long uuid, @RequestBody List<Tree> treeList) {
        return roleService.updateEmpRoles(uuid, treeList);
    }

    @GetMapping("/findOne")
    public Role findOne(Long id) {
        return roleService.findOne(id);
    }

//    @RequiresPermissions("角色设置")
    @PostMapping("/add")
    public ResponseResult add(@RequestBody Role role) {
        return roleService.add(role);
    }

//    @RequiresPermissions("角色设置")
    @PostMapping("/update")
    public ResponseResult update(@RequestBody Role roletype) {
        return roleService.update(roletype);
    }

//    @RequiresPermissions("角色设置")
    @GetMapping("/del")
    public ResponseResult del(Long id) {
        return roleService.del(id);
    }

}
