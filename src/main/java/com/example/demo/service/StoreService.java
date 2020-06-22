package com.example.demo.service;

import com.example.demo.dao.GoodsDao;
import com.example.demo.dao.StoreDao;
import com.example.demo.dao.StoredetailDao;
import com.example.demo.entity.Goods;
import com.example.demo.entity.Store;
import com.example.demo.entity.Storedetail;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import com.example.demo.entity.response.StoreGoods;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Created by more-time on 2019/6/24.
 */
@Service
public class StoreService {
    @Autowired
    private StoreDao storeDao;
    @Autowired
    private StoredetailDao storedetailDao;
    @Autowired
    private GoodsDao goodsDao;
    public ResponsePageResult findList(int page, int size, Store store) {
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

        //定义条件对象Example
        if (store == null) {
            store = new Store();
        }
        Example<Store> example = Example.of(store, exampleMatcher);

        Page<Store> all = storeDao.findAll(example, pageable);

        long total = all.getTotalElements();
        List<Store> data = all.getContent();
        return new ResponsePageResult(0, "操作成功", total, data);
    }

    public ResponseResult add(Store store) {
        Store save = storeDao.save(store);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public Store findOne(Long id) {
        Optional<Store> optional = storeDao.findById(id);
        if (optional.isPresent()) {
            return optional.get();
        } else {
            return null;
        }
    }

    public ResponseResult update(Store store) {

        Store save = storeDao.save(store);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public ResponseResult del(Long id) {
        try {
            storeDao.deleteById(id);
            return new ResponseResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(false, "操作失败");
        }
    }

    public List<Store> getStoreByEmpuuid(long empuuid) {
        List<Store> storeList = storeDao.findByEmpuuid(empuuid);
        return storeList;
    }

    public  List<StoreGoods> getStoreGoodsById( long storeuuid){
        List<Storedetail> storedetailList = storedetailDao.findByStoreuuid(storeuuid);
        List<StoreGoods> storeGoodsList = new ArrayList<>();
        for (Storedetail storedetail:
             storedetailList) {
            Goods goods = goodsDao.findById(storedetail.getGoodsuuid()).get();
            StoreGoods storeGoods = new StoreGoods();
            storeGoods.setNum(storedetail.getNum());
            storeGoods.setUuid(storedetail.getGoodsuuid());
            storeGoods.setGoodsName(goods.getName());
            storeGoodsList.add(storeGoods);
        }
        return storeGoodsList;
    }
}
