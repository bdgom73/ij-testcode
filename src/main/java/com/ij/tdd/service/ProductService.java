package com.ij.tdd.service;

import com.ij.tdd.dto.ProductParam;
import com.ij.tdd.entity.Product;

import java.util.List;

public interface ProductService {
    List<Product> getAllProduct();
    Product getProduct(Long productId);
    Long save(ProductParam productParam);
    Long update(Long productId, ProductParam productParam);
    void delete(Long productId);
}
