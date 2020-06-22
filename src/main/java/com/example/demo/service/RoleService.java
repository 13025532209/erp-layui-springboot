package com.example.demo.service;

import com.example.demo.dao.EmpDao;
import com.example.demo.dao.MenuDao;
import com.example.demo.dao.RoleDao;
import com.example.demo.entity.*;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.entity.response.Tree;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by more-time on 2019/6/28.
 */
@Service
public class RoleService {
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private EmpDao empDao;
    public List<Tree> readRoleMenus(Long uuid) {
        Optional<Menu> optional = menuDao.findById("0");
        Role role = roleDao.findById(uuid).get();
        List<Menu> roleMenus = role.getMenus();
        Menu root = optional.get();
        List<Menu> menuRoot = root.getMenus();
        List<Tree> treeList = new ArrayList<>();
        for (Menu m :
                menuRoot) {
            Tree tree = new Tree();
            tree.setId(m.getMenuid());
            tree.setTitle(m.getMenuname());
            tree.setChildren(new ArrayList<>());
            tree.setSpread(true);
            List<Menu> menus = m.getMenus();
            for (Menu menu :
                    menus) {

                Tree tree1 = new Tree();
                tree1.setId(menu.getMenuid());
                tree1.setTitle(menu.getMenuname());
                if(roleMenus.contains(menu)){
                    tree1.setChecked(true);
                }
                tree.getChildren().add(tree1);
            }
            treeList.add(tree);
        }
        return treeList;
    }

    public ResponsePageResult findList(int page, int size,Role role) {
        page--;
        if (page <= 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size,new Sort(Sort.Direction.ASC, "uuid"));

        //自定义条件查询
        //定义条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
               ;
        //定义条件对象Example
        if (role == null) {
            role = new Role();
        }
        Example<Role> example = Example.of(role, exampleMatcher);

        Page<Role> all = roleDao.findAll(example, pageable);
        long total = all.getTotalElements();
        List<Role> data = all.getContent();
        return new ResponsePageResult(0, "操作成功", total, data);
    }


    public ResponseResult updateRoleMenus(Long uuid, List<Tree> treeList){
        Role role = roleDao.findById(uuid).get();
        role.setMenus(new ArrayList<Menu>());
        List<Menu> menuList = new ArrayList<Menu>();
        for (Tree tree1:
             treeList) {
            Menu menu1 = menuDao.findById(tree1.getId()).get();
            for (Tree tree2:
                 tree1.getChildren()) {
                Menu menu2 = menuDao.findById(tree2.getId()).get();
                menuList.add(menu2);
            }

        }
        role.setMenus(menuList);
        roleDao.save(role);
        return new ResponseResult(true,"操作成功");
    }

    public List<Tree> readEmpRoles(Long empuuid) {
        Emp emp = empDao.findById(empuuid).get();
        List<Tree> treeList = new ArrayList<>();
        List<Role> roles = emp.getRoles();
        List<Role> all = roleDao.findAll();
        for (Role role:
                all) {
            Tree tree = new Tree();
            tree.setTitle(role.getName());
            tree.setId(role.getUuid()+"");
            if(roles.contains(role)){
                tree.setChecked(true);
            }
            treeList.add(tree);
        }
        return treeList;
    }


    public ResponseResult updateEmpRoles(Long uuid, List<Tree> treeList){
        Emp emp = empDao.findById(uuid).get();
        emp.setRoles(new ArrayList<>());
        List<Role> roles = new ArrayList<>();
        for (Tree tree:
             treeList) {
            Role role = roleDao.findById(Long.parseLong(tree.getId())).get();
            roles.add(role);
        }
        emp.setRoles(roles);
        empDao.save(emp);
        return new ResponseResult(true,"操作成功");
    }

    public Emp testJpa(Long uuid) {
        Emp one = empDao.findById(uuid).get();
        List<Role> roles = one.getRoles();
       /* System.out.println(roles);*/
       /* Emp one = empDao.findByUuid(uuid);*/
        return one;
    }

    public Role findOne(Long id) {
        Optional<Role> optional = roleDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }


    public ResponseResult add(Role role) {
        Role save = roleDao.save(role);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public ResponseResult update(Role role) {

        Role save = roleDao.save(role);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public ResponseResult del(Long id) {
        try {
            roleDao.deleteById(id);
            return new ResponseResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(false, "操作失败");
        }
    }
}
