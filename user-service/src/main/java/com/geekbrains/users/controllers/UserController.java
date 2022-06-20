package com.geekbrains.users.controllers;

import contract.entities.SystemUser;
import contract.entities.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserController {

    @GetMapping("/getUser/{username}")
    User findByUserName(@PathVariable("username") String username);

    @GetMapping("/getUsers")
    List<User> getUsers();

    @PostMapping("/saveUser")
    boolean save(@RequestBody SystemUser systemUser);
}
