package com.springboot.www.service;

import com.springboot.www.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {
    int addNewUser(User user);
    List<User> showAllUsers();
    //void delete(User user);
    int deleteById(int id);
    int updateById(User user);
    int updatePhotoById(int uid,String photo);
    List<User> findByLike(String condition);
    User login(String name,String password);
    //User getById(String id);
    //List<User> getUsers();
}
