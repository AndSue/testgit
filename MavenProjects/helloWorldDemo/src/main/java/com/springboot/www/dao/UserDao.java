package com.springboot.www.dao;

import com.springboot.www.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
@Service
public class UserDao {
    @Autowired(required = false)
    JdbcTemplate jdbcTemplate;

    public int saveUserInformation(User user){
        List<User> list=showAllUsers();
        int id=list.get(0).getId();
        return jdbcTemplate.update("insert into user(id,name,password,tel,job,sex,photo) values(?,?,?,?,?,?,?)",
                id+1,user.getName(),user.getPassword(),user.getTel(),user.getJob(),user.getSex(),"default.jpg");
    }

    public List<User> showAllUsers(){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from user order by id desc");
        List<User> userList=new ArrayList<User>();
        for(Map<String,Object> map:list){
            User user=new User();
            user.setPassword((String) map.get("password"));
            user.setName((String)map.get("name"));
            user.setJob((String)map.get("job"));
            user.setId((int)map.get("id"));
            user.setSex((String) map.get("sex"));
            user.setTel((String)map.get("tel"));
            user.setPhoto((String) map.get("photo"));
            userList.add(user);
        }
        return userList;
    }

    public int deleteById(int id){
        return jdbcTemplate.update("delete from user where id=?",id);
    }

    public int updateById(User user){
        return jdbcTemplate.update("update user set name=?,password=?,tel=?,job=?,sex=? where id=?",
                user.getName(),user.getPassword(),user.getTel(),user.getJob(),user.getSex(),user.getId());
    }

    public int updatePhotoById(int uid,String photo){
        return jdbcTemplate.update("update user set photo=? where id=?",
                photo,uid);
    }

    public List<User> findByLike(String condition){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from user where name like ? or sex=? or tel like ? or job like ?",
                "%"+condition+"%",condition,"%"+condition+"%","%"+condition+"%");
        List<User> userList=new ArrayList<User>();
        for(Map<String,Object> map:list){
            User user=new User();
            user.setPassword((String) map.get("password"));
            user.setName((String)map.get("name"));
            user.setJob((String)map.get("job"));
            user.setId((int)map.get("id"));
            user.setSex((String) map.get("sex"));
            user.setTel((String)map.get("tel"));
            userList.add(user);
        }
        return userList;
    }

    public User login(String name,String password){
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from user where name=? and password=?",
                name,password);
        User user=new User();
        if(list.size()>0){
            for(Map<String,Object> map:list){
                user.setPassword((String) map.get("password"));
                user.setName((String)map.get("name"));
                user.setJob((String)map.get("job"));
                user.setId((int)map.get("id"));
                user.setSex((String) map.get("sex"));
                user.setTel((String)map.get("tel"));
                user.setPhoto((String)map.get("photo"));
            }
            return user;
        }
        return null;
    }
}
