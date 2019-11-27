package com.harveylo.controller;

import com.harveylo.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;



@Controller
@RequestMapping("/user")
public class UserController {
    DataBaseUtility dao ;

    public UserController() {
        dao = new DataBaseUtility();
    }

    public UserController( DataBaseUtility dao) {
        this.dao = dao;
    }

    @RequestMapping("/username")
    @ResponseBody
    public User getUsername(@RequestParam("id") String id) {
        User user = new User();
        user.setUserID(id);
        user.setNickname(dao.getNickname(id));
        return user;
    }
}
