package com.example.PRM392.controller;

import com.example.PRM392.dto.ProductRequestDTO;
import com.example.PRM392.dto.UserResponseDTO;
import com.example.PRM392.entity.Product;
import com.example.PRM392.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody ProductRequestDTO request) {
        Product product = productService.createProduct(
                request.getName(),
                request.getCategoryId(),
                request.getImage(),
                request.getPrice(),
                request.getStatus()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(product);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ProductRequestDTO> getProductById(@PathVariable int id) {
        try {
            // Gọi service để lấy thông tin người dùng theo ID
            ProductRequestDTO productRequestDTO = productService.getProDuctById(id);
            return new ResponseEntity<>(productRequestDTO, HttpStatus.OK);  // Trả về thông tin người dùng với mã 200
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);  // Trả về lỗi 404 nếu người dùng không tồn tại
        }
    }
}