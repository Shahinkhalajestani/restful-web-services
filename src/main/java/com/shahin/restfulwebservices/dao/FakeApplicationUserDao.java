package com.shahin.restfulwebservices.dao;

import com.shahin.restfulwebservices.auth.ApplicationUser;
import com.shahin.restfulwebservices.configuration.security.ApplicationUserRole;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.shahin.restfulwebservices.configuration.security.ApplicationUserRole.*;

@Repository("fake")
public class FakeApplicationUserDao implements ApplicationUserDao{

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public Optional<ApplicationUser> selectApplicationUserByUserName(String username) {
        return getApplicationUsers().stream()
                .filter(applicationUser -> username.equalsIgnoreCase(applicationUser.getUsername()))
                .findFirst();
    }
    private List<ApplicationUser> getApplicationUsers(){
        ArrayList<ApplicationUser> applicationUsers = Lists.newArrayList(new ApplicationUser(
                        "shahin",
                        passwordEncoder.encode("password"),
                        STUDENT.getGrantedAuthorities(),
                        true,
                        true, true,
                        true
                ),
                new ApplicationUser(
                        "lynda",
                        passwordEncoder.encode("password"),
                        ADMIN.getGrantedAuthorities(),
                        true,
                        true, true,
                        true
                ),
                new ApplicationUser(
                        "abbas",
                        passwordEncoder.encode("password"),
                        ADMINTRAINEE.getGrantedAuthorities(),
                        true,
                        true, true,
                        true
                ));
        return applicationUsers;
    }
}
