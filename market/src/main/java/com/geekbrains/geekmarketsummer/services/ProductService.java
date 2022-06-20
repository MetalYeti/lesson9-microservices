package com.geekbrains.geekmarketsummer.services;

import contract.entities.Product;
import contract.entities.ProductDTO;
import contract.entities.RestPageImpl;
import org.springframework.web.bind.annotation.*;
import org.springframework.cloud.openfeign.FeignClient;


import java.util.List;

@FeignClient(name = "product-service-client", contextId = "product-service")
public interface ProductService {
	
	@GetMapping("/getAllProducts")
	List<Product> getAllProducts();
	
	@GetMapping("/getProductById")
	Product getProductById(@RequestParam("id") Long id);

	@RequestMapping(method = RequestMethod.GET, value ="/getProductByTitle/{title}")
	Product getProductByTitle(@PathVariable("title") String title);

	@GetMapping("/getProductsWithPagingAndFiltering")
	RestPageImpl<Product> getProductsWithPagingAndFiltering(
		@RequestParam(value = "page") int page,
		@RequestParam(value = "pageSize") int pageSize,
		@RequestParam(value = "word") String word,
		@RequestParam(value = "min") Double min,
		@RequestParam(value = "max") Double max);

	@RequestMapping("/saveProduct")
	void saveProduct(@RequestBody ProductDTO product);

	@PostMapping("/addImage")
	void addImage(@RequestParam String title, @RequestParam String imageName);
	
}
