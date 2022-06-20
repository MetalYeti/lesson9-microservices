package com.geekbrains.geekmarketsummer.utils;


import contract.entities.Product;
import contract.entities.ShoppingCart;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
public class AppLoggingAspect {

    private Logger logger = LoggerFactory.getLogger(AppLoggingAspect.class);
    private Map<String, Long> services = Collections.synchronizedMap(new HashMap<>());

    private final HttpSession httpSession;
    private SimpMessagingTemplate template;

    @Autowired
    public AppLoggingAspect(HttpSession httpSession, SimpMessagingTemplate template) {
        this.httpSession = httpSession;
        this.template = template;
    }


    @After("execution(* com.geekbrains.geekmarketsummer..*(..,contract.entities.Product+,..))")
    // pointcut expression
    public void aopSimpleMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            if (arg instanceof Product) {
                logger.info(joinPoint.getSignature() + " работает с товаром: " + ((Product) arg).getTitle());
            }
        }
    }
    @Pointcut("execution(* com.geekbrains.geekmarketsummer.services.ShoppingCartService.addToCart(..))")
    public void addToCart(){}

    @Pointcut("execution(* com.geekbrains.geekmarketsummer.services.ShoppingCartService.removeFromCart(..))")
    public void removeFromCart(){}

    @After(value="addToCart() || removeFromCart()")
    public void recalculateCart() throws InterruptedException {
        ShoppingCart cart = (ShoppingCart) httpSession.getAttribute("cart");
        logger.info("Cart total cost: " + cart.getTotalCost());
    }

    @Around("execution(* com.geekbrains.geekmarketsummer.services.*Service.*(..))")
    public Object aopWorkingTime(ProceedingJoinPoint pjp) throws Throwable {

        long start = System.currentTimeMillis();
        Object result = pjp.proceed();
        long end = System.currentTimeMillis();
        String[] tokens = pjp.getSignature().getDeclaringTypeName().split("\\.");
        String serviceName = tokens[tokens.length - 1];
        long time = end - start;

        if (services.containsKey(serviceName)) {
            services.put(serviceName, services.get(serviceName) + time);
        } else {
            services.put(serviceName, time);
        }

        logger.info(pjp.getSignature().toShortString() + " worked for " + time + " ms");

        for(String key : services.keySet()) {
            logger.info("Service " + key + " worked for " + services.get(key) + " ms so far");
        }
        return result;
    }
}
