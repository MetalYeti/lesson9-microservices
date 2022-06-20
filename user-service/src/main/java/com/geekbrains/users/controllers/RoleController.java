package com.geekbrains.users.controllers;

import contract.entities.Role;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

public interface RoleController {

    @GetMapping("/getRoles")
    List<Role> getAllRoles();
}
