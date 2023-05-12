package com.springboot.www.service;

import com.springboot.www.dao.UserDao;
import com.springboot.www.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserDao userDao=new UserDao();

    @Override
    public int addNewUser(User user) {
        return userDao.saveUserInformation(user);
    }

    public List<User> showAllUsers(){
        return userDao.showAllUsers();
    }

    @Override
    public int deleteById(int id) {
        return userDao.deleteById(id);
    }

    @Override
    public int updateById(User user) {
        return userDao.updateById(user);
    }

    @Override
    public int updatePhotoById(int uid, String photo) {
        return userDao.updatePhotoById(uid,photo);
    }

    @Override
    public List<User> findByLike(String condition) {
        return userDao.findByLike(condition);
    }

    public User login(String name,String password){
        return userDao.login(name, password);
    }
}
