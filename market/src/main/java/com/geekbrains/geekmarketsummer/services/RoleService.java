package com.geekbrains.geekmarketsummer.services;

import contract.entities.Role;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "user-service-client", contextId = "role-service")
public interface RoleService {
    @GetMapping("/getRoles")
    List<Role> getAllRoles();
}
