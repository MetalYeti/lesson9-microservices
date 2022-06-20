package com.geekbrains.users.services;

import com.geekbrains.users.repositories.RoleRepository;
import com.geekbrains.users.repositories.UserRepository;
import contract.entities.SystemUser;
import contract.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Autowired
    public void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    public void setPasswordEncoder(BCryptPasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Transactional
    public User findByUserName(String username) {
        return userRepository.findOneByUserName(username);
    }

    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public boolean save(SystemUser systemUser) {
        User user = new User();

        if (findByUserName(systemUser.getUserName()) != null) {
            return false;
        }

        user.setUserName(systemUser.getUserName());
        user.setPassword(passwordEncoder.encode(systemUser.getPassword()));
        user.setFirstName(systemUser.getFirstName());
        user.setLastName(systemUser.getLastName());
        user.setEmail(systemUser.getEmail());
//        user.setRoles(Arrays.stream(systemUser.getRoles()).map(roleId->roleRepository.findById(Long.parseLong(roleId)).get()).collect(Collectors.toList()));
//        user.setRoles(systemUser.getRoles().stream().map(roleId->roleRepository.findById(roleId).get()).collect(Collectors.toList()));
        user.setRoles(systemUser.getRoles().stream().map(role -> roleRepository.findOneByName(role)).collect(Collectors.toList()));
        userRepository.save(user);
        return true;
    }
}
