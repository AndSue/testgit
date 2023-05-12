package com.springboot.www.service;

import com.springboot.www.dao.PositionDao;
import com.springboot.www.entity.Position;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PositionServiceImpl implements PositionService{
    @Autowired
    private PositionDao positionDao=new PositionDao();

    @Override
    public List<Position> findByUid(int uid) {
        return positionDao.findByUid(uid);
    }

    @Override
    public int addNewPosition(Position position) {
        return positionDao.addNewPosition(position);
    }

    @Override
    public int updateById(Position position) {
        return positionDao.updateById(position);
    }

    @Override
    public int deleteById(int id,int uid,boolean isDefault) {
        return positionDao.deleteById(id,uid,isDefault);
    }

    @Override
    public Position findById(int id) {
        return positionDao.findById(id);
    }
}
