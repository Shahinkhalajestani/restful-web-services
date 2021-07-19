package com.shahin.restfulwebservices.repository;

import com.shahin.restfulwebservices.dao.UserDao;
import com.shahin.restfulwebservices.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.text.ParseException;
import java.util.List;

@Repository
public class UserRepository {

    @Autowired
    private UserDao userDao;

    public List<User> getUsers()  {
       return userDao.findAll();
    }

}
