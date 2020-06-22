package com.example.demo.service;

import com.example.demo.dao.EmpDao;
import com.example.demo.dao.EmpMapper;
import com.example.demo.dao.MenuDao;
import com.example.demo.entity.Emp;
import com.example.demo.entity.Menu;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by more-time on 2019/6/23.
 */
@Service
public class EmpService {
    @Autowired
    private EmpDao empDao;
    @Autowired
    private MenuDao menuDao;
    @Autowired
    private EmpMapper empMapper;

    public List<Emp> list() {
        return empDao.findAll();
    }

    public ResponsePageResult findList(int page, int size, Emp emp) {

        page--;
        if (page <= 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        Sort sort = new Sort(Sort.Direction.ASC, "uuid");
        Specification<Emp> specification = getWhereClause(emp);
        Page<Emp> all = empDao.findAll(specification, PageRequest.of(page, size, sort));
        List<Emp> data = all.getContent();
        long total = all.getTotalElements();
        return new ResponsePageResult(0, "操作成功", total, data);
    }

    public ResponseResult add(Emp emp) {
        Emp save = empDao.save(emp);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public Emp findOne(Long id) {
        Optional<Emp> optional = empDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    public ResponseResult update(Emp emp) {

        Emp save = empDao.save(emp);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public ResponseResult del(Long id) {
        try {
            empDao.deleteById(id);
            return new ResponseResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(false, "操作失败");
        }
    }

    public Menu readMenusByEmpuuid(Long uuid) {
        //获取所有的菜单，做模板用的
        Menu root = menuDao.findById("0").get();
        //用户下的菜单集合
        List<Menu> empMenus = empMapper.getMenusByEmpuuid(uuid);
        //根菜单
        Menu menu = cloneMenu(root);

        //循环匹配模板
        //一级菜单
        Menu _m1 = null;
        Menu _m2 = null;
        for (Menu m1 : root.getMenus()) {
            _m1 = cloneMenu(m1);
            //二级菜单循环
            for (Menu m2 : m1.getMenus()) {
                //用户包含有这个菜单
                if (empMenus.contains(m2)) {
                    //复制菜单
                    _m2 = cloneMenu(m2);
                    //加入到上级菜单下
                    _m1.getMenus().add(_m2);
                }
            }
            //有二级菜单我们才加进来
            if (_m1.getMenus().size() > 0) {
                //把一级菜单加入到根菜单下
                menu.getMenus().add(_m1);
            }
        }
        return menu;
    }

    /**
     * 复制menu
     *
     * @param src
     * @return
     */
    private Menu cloneMenu(Menu src) {
        Menu menu = new Menu();
        menu.setIcon(src.getIcon());
        menu.setMenuid(src.getMenuid());
        menu.setMenuname(src.getMenuname());
        menu.setUrl(src.getUrl());
        menu.setMenus(new ArrayList<Menu>());
        return menu;
    }


    /**
     * 动态生成where语句
     */
    private Specification<Emp> getWhereClause(final Emp emp) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new Specification<Emp>() {
            @Override
            public Predicate toPredicate(Root<Emp> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if (StringUtils.isNotEmpty(emp.getBirthday1())) {
                    try {
                        Date birthday1 = simpleDateFormat.parse(emp.getBirthday1());
                        predicate.add(cb.greaterThanOrEqualTo(root.get("birthday").as(Date.class), birthday1));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (StringUtils.isNotEmpty(emp.getBirthday2())) {
                    try {
                        Date birthday2 = simpleDateFormat.parse(emp.getBirthday2());
                        predicate.add(cb.lessThanOrEqualTo(root.get("birthday").as(Date.class), birthday2));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                if (StringUtils.isNotEmpty(emp.getUsername())) {
                    predicate.add(cb.like(root.get("username").as(String.class), "%" + emp.getUsername() + "%"));
                }
                if (StringUtils.isNotEmpty(emp.getName())) {
                    predicate.add(cb.like(root.get("name").as(String.class), "%" + emp.getName() + "%"));
                }
                if (StringUtils.isNotEmpty(emp.getEmail())) {
                    predicate.add(cb.like(root.get("email").as(String.class), "%" + emp.getEmail() + "%"));
                }
                if (StringUtils.isNotEmpty(emp.getTele())) {
                    predicate.add(cb.like(root.get("tele").as(String.class), "%" + emp.getTele() + "%"));
                }
                if (StringUtils.isNotEmpty(emp.getAddress())) {
                    predicate.add(cb.like(root.get("address").as(String.class), "%" + emp.getAddress() + "%"));
                }
                if (emp.getDepuuid() != null) {
                    predicate.add(cb.equal(root.get("depuuid").as(String.class), emp.getDepuuid()));
                }
                if (emp.getGender() != null) {
                    predicate.add(cb.equal(root.get("gender").as(String.class), emp.getGender()));
                }

                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    public ResponseResult resetPwd(Emp emp) {
        Optional<Emp> optional = empDao.findById(emp.getUuid());
        if (optional.isPresent()) {
            Emp emp1 = optional.get();
            emp1.setPwd(emp.getPwd());
            empDao.save(emp1);
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }


}
