package com.ij.tdd.service;

import com.ij.tdd.dto.ProductParam;
import com.ij.tdd.entity.Product;
import com.ij.tdd.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class DefaultProductService implements ProductService{

    private final ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long productId) {
        return productRepository.findById(productId).orElseGet(null);
    }

    @Override
    @Transactional
    public Long save(ProductParam productParam) {
        Product product = Product.createProduct(
                productParam.getName(),
                productParam.getPrice(),
                productParam.getContents());

        Product save = productRepository.save(product);
        return save.getId();
    }

    @Override
    @Transactional
    public Long update(Long productId, ProductParam productParam) {
        Product product = getProduct(productId);
        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품");
        }
        product.changeProduct(productParam.getName(), product.getPrice(), product.getContents());
        return product.getId();
    }

    @Override
    @Transactional
    public void delete(Long productId) {
        Product product = getProduct(productId);
        if (product == null) {
            throw new IllegalArgumentException("존재하지 않는 상품");
        }
        productRepository.delete(product);
    }
}/////
