package com.geekbrains.geekmarketsummer.utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

//@Aspect
//@Component
public class AspectAuthChecker {
	
//	@Around("execution(public * com.geekbrains.services.ProductService.saveProduct(..))")
//	public Object aopAdminCheck(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
//
//		SecurityContext securityContext = SecurityContextHolder.getContext();
//		Authentication authentication = securityContext.getAuthentication();
//		Collection<?> authorities = authentication.getAuthorities();
//		if(authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))){
//			System.out.println("You are admin!");
//			return proceedingJoinPoint.proceed();
//		}
//		return null;
//	}
//
//	@Before("execution(public void com.geekbrains.geekmarketwinter.com.geekbrains.geekmarketwinter.services.ShoppingCartService.AddToCart(..))")
//	public void aopSimpleMethod() {
//		System.out.println("Добавлен товар в корзину");
//	}

}
