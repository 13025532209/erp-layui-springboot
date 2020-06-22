package com.example.demo.service;

import com.example.demo.dao.OrderdetailDao;
import com.example.demo.dao.OrdersDao;
import com.example.demo.entity.Emp;
import com.example.demo.entity.Orderdetail;
import com.example.demo.entity.Orders;
import com.example.demo.entity.request.GoodsList_Supplier_type;
import com.example.demo.entity.request.GoodsQuvo;
import com.example.demo.entity.response.OrdersQuvo;
import com.example.demo.entity.response.ResponsePageResult;
import com.example.demo.entity.response.ResponseResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.aspectj.weaver.ast.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by more-time on 2019/6/23.
 */
@Service
public class OrdersService {
    @Autowired
    private OrdersDao ordersDao;
    @Autowired
    private OrderdetailDao orderdetailDao;

    public List<Orders> list() {
        return ordersDao.findAll();
    }

    public ResponsePageResult findList(int page, int size, Orders orders) {

        page--;
        if (page <= 0) {
            page = 0;
        }
        if (size <= 0) {
            size = 10;
        }
        Sort sort = new Sort(Sort.Direction.DESC, "createtime");
        Specification<Orders> specification = getWhereClause(orders);
        Page<Orders> all = ordersDao.findAll(specification, PageRequest.of(page, size, sort));
        List<Orders> data = all.getContent();
        long total = all.getTotalElements();
        return new ResponsePageResult(0, "操作成功", total, data);
    }

    public ResponseResult add(GoodsList_Supplier_type more) {
        try {
            Subject subject = SecurityUtils.getSubject();
            Emp emp = (Emp)subject.getPrincipal();
//            if(Orders.TYPE_IN.equals(more.getType())){
//                if(!subject.isPermitted("采购订单申请")){
//                    throw new UnauthorizedException("权限不足");
//                }
//            }
//
//            else if(Orders.TYPE_OUT.equals(more.getType())){
//                if(!subject.isPermitted("销售订单录入")){
//                    throw new UnauthorizedException("权限不足");
//                }
//            }else{
//                throw new UnauthorizedException("参数非法");
//            }

            Orders orders = new Orders();
            orders.setType(more.getType());
            orders.setCreater(emp.getUuid());
            orders.setCreatetime(new Date());
            orders.setSupplieruuid(more.getSupplier().getUuid());
            orders.setTotalmoney(more.getTotal());
            orders.setState(Orders.STATE_CREATE);

            Orders save = ordersDao.save(orders);
            List<GoodsQuvo> list = more.getGoodsList();
            for (GoodsQuvo goodsQuvo :
                    list) {
                Orderdetail orderdetail = new Orderdetail();
                orderdetail.setGoodsuuid(goodsQuvo.getGoods().getUuid());
                orderdetail.setGoodsname(goodsQuvo.getGoods().getName());
                orderdetail.setPrice(goodsQuvo.getGoods().getInprice());
                orderdetail.setNum(goodsQuvo.getNum());
                orderdetail.setMoney(goodsQuvo.getMoney());
                orderdetail.setState(Orderdetail.STATE_NOT_IN);
                orderdetail.setOrdersuuid(save.getUuid());
                orderdetailDao.save(orderdetail);
            }
            return new ResponseResult(true, "操作成功");
        }
        catch (UnauthorizedException u){
            return new ResponseResult(false, u.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(false, "操作失败");
        }
    }

    public OrdersQuvo findOne(Long id) {
        Optional<Orders> optional = ordersDao.findById(id);
        if (optional.isPresent()) {
            Orders orders = optional.get();
            List<Orderdetail> orderdetailList = orderdetailDao.findByOrdersuuid(orders.getUuid());
            OrdersQuvo ordersQuvo = new OrdersQuvo();
            ordersQuvo.setOrderdetailList(orderdetailList);
            ordersQuvo.setOrders(orders);
            return ordersQuvo;
        } else {
            return null;
        }
    }

    public ResponseResult update(Orders orders) {

        Orders save = ordersDao.save(orders);
        if (save != null) {
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public ResponseResult del(Long id) {
        try {
            ordersDao.deleteById(id);
            return new ResponseResult(true, "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseResult(false, "操作失败");
        }
    }


    /**
     * 动态生成where语句
     */
    private Specification<Orders> getWhereClause(final Orders orders) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return new Specification<Orders>() {
            @Override
            public Predicate toPredicate(Root<Orders> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicate = new ArrayList<>();
                if (StringUtils.isNotEmpty(orders.getType())) {
                    predicate.add(cb.equal(root.get("type").as(String.class), orders.getType()));
                }
                if (StringUtils.isNotEmpty(orders.getState())) {
                    predicate.add(cb.equal(root.get("state").as(String.class), orders.getState()));
                }
                if (orders.getSupplieruuid() != null) {
                    predicate.add(cb.equal(root.get("supplieruuid").as(String.class), orders.getSupplieruuid()));
                }
                Predicate[] pre = new Predicate[predicate.size()];
                return query.where(predicate.toArray(pre)).getRestriction();
            }
        };
    }

    public ResponseResult updateState(Long id,String state) {
        Optional<Orders> optional = ordersDao.findById(id);
        if(optional.isPresent()){
            Orders orders = optional.get();
            orders.setState(state);
            Emp emp = getCurrentUser();
//            采购: 0:未审核 1:已审核, 2:已确认, 3:已入库；
            if(Orders.STATE_CHECK.equals(state)){
                orders.setChecktime(new Date());
                orders.setChecker(emp.getUuid());
            }else if(Orders.STATE_START.equals(state)){
                orders.setStarttime(new Date());
                orders.setStarter(emp.getUuid());
            }else if(Orders.STATE_END.equals(state)){
                orders.setEndtime(new Date());
                orders.setEnder(emp.getUuid());
            }
            ordersDao.save(orders);
            return new ResponseResult(true, "操作成功");
        }
        return new ResponseResult(false, "操作失败");
    }

    public Emp getCurrentUser(){
        Subject subject = SecurityUtils.getSubject();
        Emp emp = (Emp)subject.getPrincipal();
        return emp;
    }
}
