package com.example.demo.service;

import com.example.demo.dao.GoodsDao;
import com.example.demo.dao.GoodsTypeDao;
import com.example.demo.entity.Goods;
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
public class GoodsService {
    @Autowired
    private GoodsDao goodsDao;

    public ResponsePageResult findList(int page, int size, Goods goods) {
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
                .withMatcher("name", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("origin", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("producer", ExampleMatcher.GenericPropertyMatchers.contains())
                .withMatcher("unit", ExampleMatcher.GenericPropertyMatchers.contains());

        //定义条件对象Example
        if (goods == null) {
            goods = new Goods();
        }
        Example<Goods> example = Example.of(goods, exampleMatcher);

        Page<Goods> all = goodsDao.findAll(example, pageable);

        long total = all.getTotalElements();
        List<Goods> data = all.getContent();
        return new ResponsePageResult(0, "操作成功", total, data);
    }

    public ResponseResult add(Goods goods) {
        Goods save = goodsDao.save(goods);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public Goods findOne(Long id) {
        Optional<Goods> optional = goodsDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    public ResponseResult update(Goods goods) {

        Goods save = goodsDao.save(goods);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public ResponseResult del(Long id) {
        try {
            goodsDao.deleteById(id);
            return new ResponseResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(false, "操作失败");
        }
    }

    public List<Goods> list() {
        return goodsDao.findAll();
    }
}
