package com.example.demo.service;

import com.example.demo.dao.GoodsTypeDao;
import com.example.demo.entity.Emp;
import com.example.demo.entity.Goodstype;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by more-time on 2019/6/23.
 */
@Service
public class GoodsTypeService {
    @Autowired
    private GoodsTypeDao goodsTypeDao;

    public ResponsePageResult findList(int page, int size, Goodstype goodstype) {
        page--;
        if (page <= 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        Pageable pageable = PageRequest.of(page, size);

        //自定义条件查询
        //定义条件匹配器
        ExampleMatcher exampleMatcher = ExampleMatcher.matching()
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains());

        //条件值对象
        Goodstype goodstype1 = new Goodstype();
        //设置条件值
        if (StringUtils.isNotEmpty(goodstype.getName())) {
            goodstype1.setName(goodstype.getName());
        }
        //定义条件对象Example
        Example<Goodstype> example = Example.of(goodstype1, exampleMatcher);

        Page<Goodstype> all = goodsTypeDao.findAll(example, pageable);

        long total = all.getTotalElements();
        List<Goodstype> data = all.getContent();
        return new ResponsePageResult(0,"操作成功",total,data);
    }

    public ResponseResult add(Goodstype goodstype) {
        Goodstype save = goodsTypeDao.save(goodstype);
        if(save!=null){
            return new ResponseResult(true,"操作成功");
        }else{
            return new ResponseResult(false,"操作失败");
        }
    }

    public Goodstype findOne(Long id) {
        Optional<Goodstype> optional = goodsTypeDao.findById(id);
        if(optional.isPresent()){
            return optional.get();
        }else{
            return null;
        }
    }

    public ResponseResult update(Goodstype goodstype) {
        Optional<Goodstype> optional = goodsTypeDao.findById(goodstype.getUuid());
        if(optional.isPresent()){
            Goodstype goodstype1 = optional.get();
            goodstype1.setName(goodstype.getName());
            goodsTypeDao.save(goodstype1);
            return new ResponseResult(true,"操作成功");
        }else{
            return new ResponseResult(false,"操作失败");
        }
    }

    public ResponseResult del(Long id) {
        try {
            goodsTypeDao.deleteById(id);
            return new ResponseResult(true,"操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(false,"操作失败");
        }
    }

    public List<Goodstype> list() {
        return goodsTypeDao.findAll();
    }
}
