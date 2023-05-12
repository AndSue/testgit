package com.springboot.www.service;

import com.springboot.www.dao.GoodDao;
import com.springboot.www.entity.Good;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface GoodService {
    List<Good> findAllGoods();
    List<Good> findByLike(String condition);
    List<Good> findByType(String type);
    Good findById(String id);
    List<Good> findByTags(String[] tags);
}
