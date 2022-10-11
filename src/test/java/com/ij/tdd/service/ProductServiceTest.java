package com.ij.tdd.service;

import com.ij.tdd.dto.ProductParam;
import com.ij.tdd.entity.Product;
import com.ij.tdd.repository.ProductRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.AdditionalAnswers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@Import({DefaultProductService.class, ProductRepository.class})
class ProductServiceTest {
    @MockBean
    ProductRepository productRepository;

    @Autowired
    DefaultProductService productService;

    @Test
    @DisplayName("상품 전체 조회")
    void getProducts() {
        // given
        when(productRepository.findAll())
                .thenReturn(createProductList());

        // when
        List<Product> result = productService.getAllProduct();

        // then
        assertThat(result.size()).isEqualTo(5);
        assertThat(result).extracting("name")
                .contains("product1", "product2", "product3", "product4", "product5");

        verify(productRepository).findAll();
    }

    @Test
    @DisplayName("상품 단일 조회")
    void getProduct() {
        // given
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(Product.createProduct("product1", 0, "product1 is ...")));

        // when
        Product product = productService.getProduct(1L);


        // then
        assertThat(product.getName()).isEqualTo("product1");
        assertThat(product.getPrice()).isEqualTo(0);

        verify(productRepository).findById(1L);
    }

    @Test
    @DisplayName("상품 저장")
    void saveProduct() {
        // given
        when(productRepository.save(Mockito.any(Product.class)))
                .then(returnsFirstArg())
                .thenReturn(Product.createProduct(1L, "product1", 1000, "product1 is ..."));

        ProductParam productParam = new ProductParam("product1", "product1 is ...", 1000);

        when(productService.save(productParam)).thenReturn(1L);

        // when
        Long productId = productService.save(productParam);

        // then
        assertThat(productId).isEqualTo(1L);

        verify(productRepository).save(Mockito.any(Product.class));
    }

    @Test
    @DisplayName("상품 수정")
    void updateProduct() {
        // given
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(Product.createProduct(1L , "product1", 1000, "product1 is ...")));

        ProductParam productParam = new ProductParam("product1", "product1 is ...", 1000);

        // when
        Long productId = productService.update(1L, productParam);

        // then
        assertThat(productId).isEqualTo(1L);

        verify(productRepository).findById(1L);
    }

    @Test
    @DisplayName("상품 삭제")
    public void deleteProduct() {
        // given
        when(productRepository.findById(1L))
                .thenReturn(Optional.of(Product.createProduct(1L , "product1", 1000, "product1 is ...")));

        // when
        productService.delete(1L);

        // then
        verify(productRepository).delete(Mockito.any(Product.class));
    }

    private List<Product> createProductList() {
        List<Product> result = new ArrayList<>();
        for (int i = 1; i < 6 ; i++) {
            result.add(Product.createProduct(
                    Long.parseLong(Integer.toString(i)),
                    "product".concat(Integer.toString(i)),
                    i * 100,
                    "product".concat(Integer.toString(i)).concat(" is ...")
            ));
        }
        return result;
    }
}