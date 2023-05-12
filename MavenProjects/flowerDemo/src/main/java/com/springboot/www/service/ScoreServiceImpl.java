package com.springboot.www.service;

import com.springboot.www.dao.ScoreDao;
import com.springboot.www.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScoreServiceImpl implements ScoreService{
    @Autowired
    ScoreDao scoreDao=new ScoreDao();

    @Override
    public List<Score> findByFid(String fid) {
        return scoreDao.findByFid(fid);
    }

    @Override
    public List<Score> findByUid(int uid) {
        return scoreDao.findByUid(uid);
    }

    @Override
    public int addNewScore(Score score) {
        return scoreDao.addNewScore(score);
    }
}
