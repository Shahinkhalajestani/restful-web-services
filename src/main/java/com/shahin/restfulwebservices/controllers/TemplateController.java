package com.shahin.restfulwebservices.controllers;

import com.shahin.restfulwebservices.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/")
public class TemplateController {

    @Autowired
    private UsersController usersController;

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
