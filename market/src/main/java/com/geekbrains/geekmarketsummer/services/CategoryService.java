package com.geekbrains.geekmarketsummer.services;

import contract.entities.Category;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient(name = "product-service-client", contextId = "category-service")
public interface CategoryService {
	
	@GetMapping("/getAllCategories")
	List<Category> getAllCategories();
	
	@GetMapping("/getCategory")
	Category getCategoryById(Long id);
}
