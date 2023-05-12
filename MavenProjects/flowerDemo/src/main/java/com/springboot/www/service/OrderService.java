package com.springboot.www.service;

import com.springboot.www.entity.Order;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    int addNewOrder(Order order);
    List<Order> findNotSendByUid(int uid);
    List<Order> findSendByUid(int uid);
    List<Order> findReceiveByUid(int uid);
    List<Order> findDeletedByUid(int uid);
    int deleteById(String id);
    int updateReceiveByUid(String id);
    List<Order> findByUid(int uid);
}
