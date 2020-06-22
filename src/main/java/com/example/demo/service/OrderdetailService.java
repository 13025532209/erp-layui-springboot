package com.example.demo.service;

import com.example.demo.dao.OrderdetailDao;
import com.example.demo.dao.OrdersDao;
import com.example.demo.dao.StoredetailDao;
import com.example.demo.dao.StoreoperDao;
import com.example.demo.entity.Orderdetail;
import com.example.demo.entity.Orders;
import com.example.demo.entity.Storedetail;
import com.example.demo.entity.Storeoper;
import com.example.demo.entity.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by more-time on 2019/6/27.
 */
@Service
public class OrderdetailService {
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private OrderdetailDao orderdetailDao;
    @Autowired
    private StoredetailDao storedetailDao;
    @Autowired
    private StoreoperDao storeoperDao;
    /**
     * 入库
     */

    @Transactional(rollbackFor = Exception.class)
    public ResponseResult doInStore(Long uuid, Long storeuuid, Long empuuid){
        //******** 第1步 更新商品明细**********
        //1. 获取明细信息
        Orderdetail detail = orderdetailDao.findById(uuid).get();
        //2. 判断明细的状态，一定是未入库的
        if(!Orderdetail.STATE_NOT_IN.equals(detail.getState())){
            return new ResponseResult(false,"不能重复入库");
        }
        //3. 修改状态为已入库
        detail.setState(Orderdetail.STATE_IN);
        //4. 入库时间
        detail.setEndtime(new Date());
        //5. 库管员
        detail.setEnder(empuuid);
        //6. 入到哪个仓库
        detail.setStoreuuid(storeuuid);

        //*******第2 步 更新商品仓库库存*********
        //1. 构建查询的条件
        Storedetail storedetail = new Storedetail();
        storedetail.setGoodsuuid(detail.getGoodsuuid());
        storedetail.setStoreuuid(storeuuid);
        //2. 通过查询 检查是否存在库存信息
        Storedetail store = storedetailDao.findByGoodsuuidAndStoreuuid(storedetail.getGoodsuuid(),storedetail.getStoreuuid());
        if(store!=null){
            //存在的话，则应该累加它的数量
            long num = 0;
            if(null != store.getNum()){
                num = store.getNum().longValue();
            }
            store.setNum(num + detail.getNum());
        }else{
            //不存在，则应该插入库存的记录
            storedetail.setNum(detail.getNum());
            storedetailDao.save(storedetail);
        }

        //****** 第3步 添加操作记录*****
        //1. 构建操作记录
        Storeoper log = new Storeoper();
        log.setEmpuuid(empuuid);
        log.setGoodsuuid(detail.getGoodsuuid());
        log.setNum(detail.getNum());
        log.setOpertime(detail.getEndtime());
        log.setStoreuuid(storeuuid);
        log.setType(Storeoper.TYPE_IN);
        //2. 保存到数据库中
        storeoperDao.save(log);

        //**** 第4步 是否需要更新订单的状态********
        Orders orders = ordersDao.findById(detail.getOrdersuuid()).get();
        List<Orderdetail> orderdetailList = orderdetailDao.findByOrdersuuidAndState(orders.getUuid(), Orderdetail.STATE_NOT_IN);
        if(orderdetailList.size()==0){
            orders.setState(Orders.STATE_END);
            orders.setEndtime(detail.getEndtime());
            orders.setEnder(empuuid);
        }
        ordersDao.save(orders);
        return new ResponseResult(true,"入库成功");
    }


    /**
     * 出库
     */
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult doOutStore(Long uuid,Long storeuuid, Long empuuid){
        //******** 第1步 更新商品明细**********
        //1. 获取明细信息
        Orderdetail detail = orderdetailDao.findById(uuid).get();
        //2. 判断明细的状态，一定是未入库的
        if(!Orderdetail.STATE_NOT_IN.equals(detail.getState())){
            return new ResponseResult(false,"不能重复入库");
        }
        //3. 修改状态为已出库
        detail.setState(Orderdetail.STATE_IN);
        //4. 出库时间
        detail.setEndtime(new Date());
        //5. 库管员
        detail.setEnder(empuuid);
        //6. 从哪个仓库出
        detail.setStoreuuid(storeuuid);


        //*******第2 步 更新商品仓库库存*********
        //1. 构建查询的条件
        Storedetail storedetail = new Storedetail();
        storedetail.setGoodsuuid(detail.getGoodsuuid());
        storedetail.setStoreuuid(storeuuid);
        //2. 通过查询 检查是否存在库存信息
        Storedetail store = storedetailDao.findByGoodsuuidAndStoreuuid(storedetail.getGoodsuuid(),storedetail.getStoreuuid());
        if(store!=null){
            long num = store.getNum() - detail.getNum();
            if(num<0){
                return new ResponseResult(false,"库存不足");
            }
            store.setNum(num);
        }else{
           return new ResponseResult(false,"库存不足");
        }

        //****** 第3步 添加操作记录*****
        //1. 构建操作记录
        Storeoper log = new Storeoper();
        log.setEmpuuid(empuuid);
        log.setGoodsuuid(detail.getGoodsuuid());
        log.setNum(detail.getNum());
        log.setOpertime(detail.getEndtime());
        log.setStoreuuid(storeuuid);
        log.setType(Storeoper.TYPE_OUT);
        //2. 保存到数据库中
        storeoperDao.save(log);


        //**** 第4步 是否需要更新订单的状态********
        Orders orders = ordersDao.findById(detail.getOrdersuuid()).get();
        List<Orderdetail> orderdetailList = orderdetailDao.findByOrdersuuidAndState(orders.getUuid(), Orderdetail.STATE_NOT_OUT);
        if(orderdetailList.size()==0){
            orders.setState(Orders.STATE_OUT);
            orders.setEndtime(detail.getEndtime());
            orders.setEnder(empuuid);
        }
        ordersDao.save(orders);
        return new ResponseResult(true,"出库成功");
    }
}
