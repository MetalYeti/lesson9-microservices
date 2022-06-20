package com.geekbrains.geekmarketsummer.controllers;

import com.geekbrains.geekmarketsummer.services.ShoppingCartService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;

@Log4j2
@Controller
public class WsCartController {

    private final ShoppingCartService shoppingCartService;

    @Autowired
    public WsCartController(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @MessageMapping("/message")
    @SendToUser("/queue/summ")
    public BigDecimal getMessages(Message<BigDecimal> message) throws InterruptedException {
        HttpSession session = (HttpSession) SimpMessageHeaderAccessor.getSessionAttributes(message.getHeaders()).get("HTTP_SESSION");
        BigDecimal cartSum = shoppingCartService.getTotalCost(session);
        log.info(cartSum);
        return cartSum;
    }
}
