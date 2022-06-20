package com.geekbrains.geekmarketsummer.services;

import contract.entities.SystemUser;
import contract.entities.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@FeignClient(name = "user-service-client", contextId = "user-service")
public interface UserService{
    @GetMapping("/getUser/{username}")
    User findByUserName(@PathVariable("username")String username);

    @PostMapping("/saveUser")
    boolean save(SystemUser systemUser);

    @GetMapping("/getUsers")
    List<User> getUsers();
}
