package com.shahin.restfulwebservices.dao;

import com.shahin.restfulwebservices.auth.ApplicationUser;

import java.util.Optional;

public interface ApplicationUserDao {
     Optional<ApplicationUser> selectApplicationUserByUserName(String username);
}
