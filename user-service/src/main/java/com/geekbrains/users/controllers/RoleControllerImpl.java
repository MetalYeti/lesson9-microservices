package com.geekbrains.users.controllers;

import com.geekbrains.users.services.RoleService;
import contract.entities.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RoleControllerImpl implements RoleController{

    private RoleService roleService;

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Override
    public List<Role> getAllRoles() {
        return roleService.getRoles();
    }
}
