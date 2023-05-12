package com.springboot.www.dao;

import com.springboot.www.entity.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class ScoreDao {
    @Autowired(required = false)
    JdbcTemplate jdbcTemplate;

    public List<Score> findByFid(String fid){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from score where fid=?",fid);
        List<Score> scoreList=new ArrayList<>();
        for(Map<String,Object> map:list){
            Score score=new Score();
            score.setId((int) map.get("id"));
            score.setFid((String)map.get("fid"));
            score.setUid((int) map.get("uid"));
            score.setComment((String)map.get("comment"));
            score.setScore((double)map.get("score"));
            score.setPhoto((String)map.get("photo"));
            scoreList.add(score);
        }
        return scoreList;
    }

    public List<Score> findByUid(int uid){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from score where uid=?",uid);
        List<Score> scoreList=new ArrayList<>();
        for(Map<String,Object> map:list){
            Score score=new Score();
            score.setId((int) map.get("id"));
            score.setFid((String)map.get("fid"));
            score.setUid((int) map.get("uid"));
            score.setComment((String)map.get("comment"));
            score.setScore((double)map.get("score"));
            score.setPhoto((String)map.get("photo"));
            scoreList.add(score);
        }
        return scoreList;
    }

    public int addNewScore(Score score){
        //获取id
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from score order by id desc limit 1");
        int id=1;
        for(Map<String,Object> map:list) {
            id = (int) map.get("id");
        }
        id++;
        return jdbcTemplate.update("insert into score(id,uid,fid,score,comment,photo) values(?,?,?,?,?,?)",
                id,score.getUid(),score.getFid(),score.getScore(),
               score.getComment(),score.getPhoto());
    }

    public int deleteById(int id){
        return jdbcTemplate.update("delete from score where id=?",
                id);
    }
}
