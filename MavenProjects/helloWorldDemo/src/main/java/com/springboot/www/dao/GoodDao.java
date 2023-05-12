package com.springboot.www.dao;

import com.springboot.www.entity.Good;
import com.springboot.www.entity.Position;
import com.springboot.www.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
@Service
public class GoodDao {
    @Autowired(required = false)
    JdbcTemplate jdbcTemplate;

    public List<Good> findAllGoods(){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from flower");
        List<Good> goodList=new ArrayList<Good>();
        for(Map<String,Object> map:list){
            Good good=new Good();
            good.setId((String) map.get("id"));
            good.setName((String)map.get("name"));
            good.setDiscount(Double.parseDouble(map.get("discount").toString()));
            good.setPrice(Double.parseDouble(map.get("price").toString()));
            good.setNum((int)map.get("num"));
            good.setType((String) map.get("type"));
            good.setDetail((String)map.get("detail"));
            good.setPicture((String)map.get("photo"));
            goodList.add(good);
        }
        return goodList;
    }

    public List<Good> findByLike(String condition){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from flower where name like ? or detail like ? or type like ?",
                "%"+condition+"%","%"+condition+"%","%"+condition+"%");
        List<Good> goodList=new ArrayList<Good>();
        for(Map<String,Object> map:list){
            Good good=new Good();
            good.setId((String) map.get("id"));
            good.setName((String)map.get("name"));
            good.setDiscount(Double.parseDouble(map.get("discount").toString()));
            good.setPrice(Double.parseDouble(map.get("price").toString()));
            good.setNum((int)map.get("num"));
            good.setType((String) map.get("type"));
            good.setDetail((String)map.get("detail"));
            good.setPicture((String)map.get("photo"));
            goodList.add(good);
        }
        return goodList;
    }

    public List<Good> findByType(String type){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from flower where type=?", type);
        List<Good> goodList=new ArrayList<Good>();
        for(Map<String,Object> map:list){
            Good good=new Good();
            good.setId((String) map.get("id"));
            good.setName((String)map.get("name"));
            good.setDiscount(Double.parseDouble(map.get("discount").toString()));
            good.setPrice(Double.parseDouble(map.get("price").toString()));
            good.setNum((int)map.get("num"));
            good.setType((String) map.get("type"));
            good.setDetail((String)map.get("detail"));
            good.setPicture((String)map.get("photo"));
            goodList.add(good);
        }
        return goodList;
    }

    public Good findById(String id){
        //System.out.println(id);
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from flower where id=?",id);
        Good good=new Good();
        for(Map<String,Object> map:list){
            good.setId((String) map.get("id"));
            good.setName((String)map.get("name"));
            good.setDiscount(Double.parseDouble(map.get("discount").toString()));
            good.setPrice(Double.parseDouble(map.get("price").toString()));
            good.setNum((int)map.get("num"));
            good.setType((String) map.get("type"));
            good.setDetail((String)map.get("detail"));
            good.setPicture((String)map.get("photo"));
        }
        return good;
    }

    public List<Good> findByTags(String[] tags){
        String sql="select * from flower where (name like ? or detail like ? or type like ?) ";
        for(int i=0;i<3;i++){
            sql+="and (name like ? or detail like ? or type like ?) ";
        }
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql,
                "%"+tags[0]+"%","%"+tags[0]+"%","%"+tags[0]+"%",
                "%"+tags[1]+"%","%"+tags[1]+"%","%"+tags[1]+"%",
                "%"+tags[2]+"%","%"+tags[2]+"%","%"+tags[2]+"%",
                "%"+tags[3]+"%","%"+tags[3]+"%","%"+tags[3]+"%");
        List<Good> goodList=new ArrayList<Good>();
        for(Map<String,Object> map:list){
            Good good=new Good();
            good.setId((String) map.get("id"));
            good.setName((String)map.get("name"));
            good.setDiscount(Double.parseDouble(map.get("discount").toString()));
            good.setPrice(Double.parseDouble(map.get("price").toString()));
            good.setNum((int)map.get("num"));
            good.setType((String) map.get("type"));
            good.setDetail((String)map.get("detail"));
            good.setPicture((String)map.get("photo"));
            goodList.add(good);
        }
        return goodList;
    }

    public int updateById(Good good){
        return jdbcTemplate.update("update flower set name=?,detail=?,type=?,photo=?,discount=?,price=?,num=? where id=?",
                good.getName(),good.getDetail(),good.getType(),good.getPicture(),good.getDiscount(),good.getPrice(),good.getNum(),good.getId());
    }
}
