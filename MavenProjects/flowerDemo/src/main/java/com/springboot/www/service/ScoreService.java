package com.springboot.www.service;

import com.springboot.www.entity.Score;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScoreService {
    List<Score> findByFid(String fid);
    List<Score> findByUid(int uid);
    int addNewScore(Score score);
}
