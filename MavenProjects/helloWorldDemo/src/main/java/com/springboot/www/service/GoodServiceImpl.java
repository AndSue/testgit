package com.springboot.www.service;

import com.springboot.www.dao.GoodDao;
import com.springboot.www.entity.Good;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class GoodServiceImpl implements GoodService{
    @Autowired
    private GoodDao goodDao=new GoodDao();
    @Override
    public List<Good> findAllGoods() {
        return goodDao.findAllGoods();
    }

    @Override
    public List<Good> findByLike(String condition) {
        return goodDao.findByLike(condition);
    }

    @Override
    public List<Good> findByType(String type) {
        return goodDao.findByType(type);
    }
    @Override
    public Good findById(String id) {
        return goodDao.findById(id);
    }

    @Override
    public List<Good> findByTags(String[] tags) {
        return goodDao.findByTags(tags);
    }


}
