package com.example.demo.service;

import com.example.demo.dao.SupplierDao;
import com.example.demo.entity.Supplier;
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
public class SupplierService {
    @Autowired
    private SupplierDao supplierDao;

    public List<Supplier> list(){
        return supplierDao.findAll();
    }

    public ResponsePageResult findList(int page, int size, Supplier supplier) {
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
        Supplier supplier1 = new Supplier();
        if (supplier != null) {
            if(StringUtils.isNotEmpty(supplier.getName())){
                supplier1.setName(supplier.getName());
            }
            if(StringUtils.isNotEmpty(supplier.getTele())){
                supplier1.setTele(supplier.getTele());
            }
        }
        Example<Supplier> example = Example.of(supplier1, exampleMatcher);
        Page<Supplier> all = supplierDao.findAll(example, pageable);
        long total = all.getTotalElements();
        List<Supplier> data = all.getContent();
        return new ResponsePageResult(0, "操作成功", total, data);
    }

    public ResponseResult add(Supplier supplier) {
        Supplier save = supplierDao.save(supplier);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public Supplier findOne(Long id) {
        Optional<Supplier> optional = supplierDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    public ResponseResult update(Supplier supplier) {

        Supplier save = supplierDao.save(supplier);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public ResponseResult del(Long id) {
        try {
            supplierDao.deleteById(id);
            return new ResponseResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(false, "操作失败");
        }
    }
}
