package com.geekbrains.geekmarketsummer.controllers;

import contract.entities.Order;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/paypal/buy")
public class PayPalController {

	//TODO сделать оплату
	@GetMapping
	public String pay(Model model, HttpServletRequest httpServletRequest) {
		Order order = (Order)model.getAttribute("order");
		return "redirect:/order/result/".concat(order.getId().toString());
	}

}
