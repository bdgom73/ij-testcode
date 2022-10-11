package com.ij.tdd.controller;

import com.ij.tdd.dto.ApiResponse;
import com.ij.tdd.dto.ObjectResponse;
import com.ij.tdd.dto.ProductParam;
import com.ij.tdd.entity.Product;
import com.ij.tdd.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/v1/products")
    public ResponseEntity<ObjectResponse> getProducts() {
        return ResponseEntity.ok(ObjectResponse.ok(productService.getAllProduct()));
    }

    @GetMapping("/v1/products/{productId}")
    public ResponseEntity<Product> getProduct(@PathVariable("productId") Long productId) {
        return ResponseEntity.ok(productService.getProduct(productId));
    }

    @PostMapping("/v1/products")
    public ResponseEntity<ObjectResponse> save(@RequestBody ProductParam productParam) {
        return ResponseEntity.ok(ObjectResponse.ok(productService.save(productParam)));
    }

    @PutMapping("/v1/products/{productId}")
    public ResponseEntity<ObjectResponse> update(@PathVariable("productId") Long productId,
                                                 @RequestBody ProductParam productParam) {
        return ResponseEntity.ok(ObjectResponse.ok(productService.update(productId, productParam)));
    }

    @DeleteMapping("/v1/products/{productId}")
    public ResponseEntity<ApiResponse> delete(@PathVariable Long productId) {
        productService.delete(productId);
        return ResponseEntity.ok(ApiResponse.ok());
    }
}
