package com.shahin.restfulwebservices.dao;

import com.shahin.restfulwebservices.models.User;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserDaoService {
    private static List<User> users = new ArrayList<>();
    private static int userCount = 2;

    static {
        users.add(new User(1, "adam", new Date()));
        users.add(new User(2, "abbas", new Date()));
        users.add(new User(3, "shahin", new Date()));
    }

    public List<User> findAll() throws ParseException {
        users.get(0).setBirthDate(new SimpleDateFormat("dd/mm/yyyy").parse("05/04/2010"));
        return users;
    }

    public User save(User user) {
        if (user.getId() == null) {
            userCount++;
            user.setId(userCount);
            users.add(userCount,user);
        } else {
            users.get(user.getId());
        }
        return user;
    }

    public User findOne(int id) {
        if (id >= users.size()){
            return null;
        }
        return users.get(id);
    }

    public User deleteUser(int id){
        User user = this.findOne(id);
        if(user != null ){
            users.remove(id);
            return user;
        }else{
            return null;
        }
    }

}
