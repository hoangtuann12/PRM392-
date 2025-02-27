package com.example.PRM392.service;

import com.example.PRM392.dto.ProductRequestDTO;
import com.example.PRM392.entity.Category;
import com.example.PRM392.entity.Product;
import com.example.PRM392.repository.CategoryRepository;
import com.example.PRM392.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product createProduct(String name, Integer categoryId, String image, Double price, String status) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new IllegalArgumentException("Category not found"));

        Product product = new Product();
        product.setName(name);
        product.setCategory(category);
        product.setImage(image);
        product.setPrice(price != null ? price : 0.0);
        product.setStatus(status != null ? status : "available");
        product.setCreatedAt(LocalDateTime.now());

        return productRepository.save(product);
    }

    public ProductRequestDTO getProDuctById(int id) {
        Optional<Product> optionalProduct = productRepository.findById(id);
        if (optionalProduct.isPresent()) {
            Product product = optionalProduct.get();
            return new ProductRequestDTO( product.getName(),product.getCategory().getId(), product.getImage(), product.getPrice(), product.getStatus(),product.getCreatedAt());
        } else {
            throw new IllegalArgumentException("Product with ID " + id + " not found");
        }
    }
}