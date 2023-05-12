package com.springboot.www.dao;

import com.springboot.www.entity.Position;
import com.springboot.www.entity.User;
import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PositionDao {
    @Autowired(required = false)
    JdbcTemplate jdbcTemplate;

    public List<Position> findByUid(int uid){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from pos where uid=? and isShown=true",uid);
        List<Position> posList=new ArrayList<>();
        for(Map<String,Object> map:list){
            Position position=new Position();
            position.setAddress((String) map.get("pos"));
            position.setName((String)map.get("name"));
            position.setTel((String)map.get("tel"));
            position.setId((int)map.get("id"));
            position.setUid((int) map.get("uid"));
            position.setTel((String)map.get("tel"));
            position.setIsDefault((boolean) map.get("isTop"));
            position.setIsShown((boolean)map.get("isShown"));
            posList.add(position);
        }
        return posList;
    }

    public Position findById(int id){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from pos where id=?",id);
        Position position=new Position();
        for(Map<String,Object> map:list){
            position.setAddress((String) map.get("pos"));
            position.setName((String)map.get("name"));
            position.setTel((String)map.get("tel"));
            position.setId((int)map.get("id"));
            position.setUid((int) map.get("uid"));
            position.setTel((String)map.get("tel"));
            position.setIsDefault((boolean) map.get("isTop"));
            position.setIsShown((boolean)map.get("isShown"));
        }
        return position;
    }

    public int addNewPosition(Position position){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from pos order by id desc limit 1");
        int id=1;
        for(Map<String,Object> map:list){
            id=(int)map.get("id")+1;
        }
        if(findByUid(position.getUid()).size()==0){
            position.setIsDefault(true);
        }
        else{
            if(position.isDefault()==true) {
                resetDefault(position.getUid());
            }
        }
        return jdbcTemplate.update("insert into pos(id,uid,pos,name,tel,isShown,isTop) values(?,?,?,?,?,?,?)",
                id,position.getUid(),position.getAddress(),position.getName(),position.getTel(),true,position.isDefault());
    }

    public int updateById(Position position){
        deleteById(position.getId(),position.getUid(),position.isDefault());
        return addNewPosition(position);
    }

    public int setDefault(int id){
        return jdbcTemplate.update("update pos set isTop=true where id=?", id);
    }
    public int resetDefault(int uid){
        return jdbcTemplate.update("update pos set isTop=? where uid=?", false,uid);
    }

    public int deleteById(int id,int uid,boolean isDefault){
        if(isDefault==true){
            List<Position> list=findByUid(uid);
            setDefault(list.get(0).getId());
        }
        return jdbcTemplate.update("update pos set isShown=false where id=?", id);
    }
}
