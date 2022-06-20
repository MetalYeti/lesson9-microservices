package com.geekbrains.products.services;

import com.geekbrains.products.repositories.ProductRepository;
import contract.entities.Product;
import contract.entities.ProductDTO;
import contract.entities.ProductImage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    
    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private CategoryService categoryService;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }
    
    public Product getProductByTitle(String title) {
        return productRepository.findOneByTitle(title);
    }
    
    public Page<Product> getAllProductsByPage(int pageNumber, int pageSize) {
        return productRepository.findAll(PageRequest.of(pageNumber, pageSize));
    }

    public Page<Product> getProductsWithPagingAndFiltering(int pageNumber, int pageSize, Specification<Product> productSpecification) {
        return productRepository.findAll(productSpecification, PageRequest.of(pageNumber, pageSize));
    }

    public boolean isProductWithTitleExists(String productTitle) {
        return productRepository.findOneByTitle(productTitle) != null;
    }

    public void saveProduct(ProductDTO productDTO) {
        if (!isProductWithTitleExists(productDTO.getTitle())) {
            Product product = new Product();
            product.setCategory(productDTO.getCategory());
            product.setVendorCode(productDTO.getVendorCode());
            product.setTitle(productDTO.getTitle());
            product.setShortDescription(productDTO.getShortDescription());
            product.setFullDescription(productDTO.getFullDescription());
            product.setPrice(productDTO.getPrice());
    
            productRepository.save(product);
        }
    }
    
    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void addImage(Long productId, String fileName) {
        Product product = productRepository.findById(productId).get();
        ProductImage productImage = new ProductImage();
        productImage.setPath(fileName);
        productImage.setProduct(product);
        product.addImage(productImage);
        saveProduct(product);
    }
}