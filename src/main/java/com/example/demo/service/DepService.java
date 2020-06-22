package com.example.demo.service;

import com.example.demo.dao.DepDao;
import com.example.demo.entity.Dep;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by more-time on 2019/6/24.
 */
@Service
public class DepService {
    @Autowired
    private DepDao depDao;

    public List<Dep> list(){
        return depDao.findAll();
    }

    public ResponsePageResult findList(int page, int size, Dep dep) {
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
                .withMatcher("tele", ExampleMatcher.GenericPropertyMatchers.contains())
                ;
        //定义条件对象Example
        Dep dep1 = new Dep();
        if (dep != null) {
            if(StringUtils.isNotEmpty(dep.getName())){
                dep1.setName(dep.getName());
            }
            if(StringUtils.isNotEmpty(dep.getTele())){
                dep1.setTele(dep.getTele());
            }
        }
        Example<Dep> example = Example.of(dep1, exampleMatcher);
        Page<Dep> all = depDao.findAll(example, pageable);
        long total = all.getTotalElements();
        List<Dep> data = all.getContent();
        return new ResponsePageResult(0, "操作成功", total, data);
    }

    public ResponseResult add(Dep dep) {
        Dep save = depDao.save(dep);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public Dep findOne(Long id) {
        Optional<Dep> optional = depDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    public ResponseResult update(Dep dep) {

        Dep save = depDao.save(dep);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public ResponseResult del(Long id) {
        try {
            depDao.deleteById(id);
            return new ResponseResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(false, "操作失败");
        }
    }
}
