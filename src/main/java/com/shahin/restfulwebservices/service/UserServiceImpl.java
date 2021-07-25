package com.shahin.restfulwebservices.service;

import com.shahin.restfulwebservices.controllers.UserModel;
import com.shahin.restfulwebservices.exception.UserNotFoundException;
import com.shahin.restfulwebservices.models.User;
import com.shahin.restfulwebservices.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public User save(UserModel userModel) {
        User user = mapUserModelToUser(userModel);
        return this.save(user);
    }

    private User mapUserModelToUser(UserModel userModel) {
        User user = new User();
        user.setName(userModel.getName());
        user.setBirthDate(userModel.getBirthDate());
        return user;
    }

    @Override
    @Transactional
    public User getUserById(Integer id){
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFoundException("id - " + id);
        }
        else {
            return user.get();
        }
    }

    @Override
    @Transactional
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    @Transactional
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }
}
