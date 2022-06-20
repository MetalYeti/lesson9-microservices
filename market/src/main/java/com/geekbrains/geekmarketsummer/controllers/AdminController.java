package com.geekbrains.geekmarketsummer.controllers;

import contract.entities.Order;
import com.geekbrains.geekmarketsummer.services.OrderService;
import com.geekbrains.geekmarketsummer.services.UserService;
import contract.entities.Role;
import contract.entities.SystemUser;
import contract.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class AdminController {
    private OrderService orderService;
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public String showAdminDashboard(Model model) {

        model.addAttribute("users", mapUsers(userService.getUsers()));
        return "admin-panel";
    }

    @GetMapping("/orders/ready/{id}")
    public void orderReady(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        Order order = orderService.findById(id);
        orderService.changeOrderStatus(order, 2L);
        response.sendRedirect(request.getHeader("referer"));
    }

    private List<SystemUser> mapUsers(List<User> users) {
        return users.stream().map(user -> {
            SystemUser result = new SystemUser();
            result.setId(user.getId());
            result.setUserName(user.getUserName());
            result.setFirstName(user.getFirstName());
            result.setLastName(user.getLastName());
            result.setEmail(user.getEmail());
            result.setRoles(user.getRoles().stream().map(Role::getName).collect(Collectors.toList()));
            return result;
        }).collect(Collectors.toList());
    }
}
