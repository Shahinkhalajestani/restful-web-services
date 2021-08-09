package com.shahin.restfulwebservices.controllers;

import com.shahin.restfulwebservices.jwt.JwtConfig;
import com.shahin.restfulwebservices.jwt.JwtUsernameAndPasswordAuthenticationFilter;
import com.shahin.restfulwebservices.jwt.UsernameAndPasswordAuthenticationRequest;
import com.shahin.restfulwebservices.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.crypto.SecretKey;
import java.util.List;

@Controller
@RequestMapping("/")
public class TemplateController {

    @Autowired
    private UsersController usersController;
    private final SecretKey secretKey;
    private final JwtConfig jwtConfig;

    @Autowired
    public TemplateController(SecretKey secretKey, JwtConfig jwtConfig) {
        this.secretKey = secretKey;
        this.jwtConfig = jwtConfig;
    }


    @GetMapping("login")
    public String getLoginView(){
        return "login";
    }


    @GetMapping("users")
    public String getUsersList(Model model){
        List<User> users = usersController.retrieveAllUsers();
        model.addAttribute("users",users);
        return "users-list";
    }
}
