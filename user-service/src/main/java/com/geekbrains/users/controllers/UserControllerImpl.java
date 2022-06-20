package com.geekbrains.users.controllers;

import com.geekbrains.users.services.UserService;
import contract.entities.SystemUser;
import contract.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserControllerImpl implements UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public User findByUserName(String username) {
        return userService.findByUserName(username);
    }

    @Override
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @Override
    public boolean save(SystemUser systemUser) {
        return userService.save(systemUser);
    }
}
