package com.geekbrains.geekmarketsummer.controllers;

import com.geekbrains.geekmarketsummer.services.RoleService;
import contract.entities.Role;
import contract.entities.SystemUser;
import contract.entities.User;
import com.geekbrains.geekmarketsummer.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/register")
public class RegistrationController {
    private UserService userService;
    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    private final Logger logger = LoggerFactory.getLogger(RegistrationController.class);

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping("/showRegistrationForm")
    public String showMyLoginPage(Model theModel) {
        theModel.addAttribute("systemUser", new SystemUser());
        theModel.addAttribute("roles", roleService.getAllRoles());
        return "registration-form";
    }


    @PostMapping("/processRegistrationForm")
    public String processRegistrationForm(@Valid @ModelAttribute("systemUser") SystemUser theSystemUser,
                                          BindingResult theBindingResult,
                                          Model theModel) {
        String userName = theSystemUser.getUserName();
        logger.debug("Processing registration form for: " + userName);
        if (theBindingResult.hasErrors()) {
            theModel.addAttribute("roles", roleService.getAllRoles());
            return "registration-form";
        }
        User existing = userService.findByUserName(userName);
        if (existing != null) {
            // theSystemUser.setUserName(null);
            theModel.addAttribute("systemUser", theSystemUser);
            theModel.addAttribute("roles", roleService.getAllRoles());
            theModel.addAttribute("registrationError", "User with current username already exists");
            logger.debug("User name already exists.");
            return "registration-form";
        }

        userService.save(theSystemUser);
        logger.debug("Successfully created user: " + userName);
        return "registration-confirmation";
    }
}
