package com.springboot.www.service;

import com.springboot.www.dao.OrderDao;
import com.springboot.www.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    OrderDao orderDao=new OrderDao();

    @Override
    public int addNewOrder(Order order) {
        return orderDao.addNewOrder(order);
    }

    @Override
    public List<Order> findNotSendByUid(int uid) {
        return orderDao.findNotSendByUid(uid);
    }

    @Override
    public List<Order> findSendByUid(int uid) {
        return orderDao.findSendByUid(uid);
    }

    @Override
    public List<Order> findReceiveByUid(int uid) {
        return orderDao.findReceiveByUid(uid);
    }

    @Override
    public List<Order> findDeletedByUid(int uid) {
        return orderDao.findDeletedByUid(uid);
    }

    @Override
    public int deleteById(String id) {
        return orderDao.deleteById(id);
    }

    @Override
    public int updateReceiveByUid(String id) {
        return orderDao.updateReceiveByUid(id);
    }

    @Override
    public List<Order> findByUid(int uid) {
        return orderDao.findByUid(uid);
    }
}
