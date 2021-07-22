package com.shahin.restfulwebservices.service;

import com.shahin.restfulwebservices.controllers.UserModel;
import com.shahin.restfulwebservices.models.User;

import java.util.List;

public interface UserService {
    void deleteUser(Integer id);
    User save(User user);
    User getUserById(Integer id);
    List<User> getUsers();
    User save(UserModel userModel);
}
