package com.ceej.controller;

import com.ceej.model.User;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
public class UserController {
    DataBaseUtility dao = new DataBaseUtility();
    @RequestMapping("/username")
    @ResponseBody
    public User getUsername(@RequestParam("id") String id) {
        User user = null;

        return user;
    }
}
