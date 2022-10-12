package com.ij.tdd.controller;

import com.ij.tdd.entity.Product;
import com.ij.tdd.service.ProductService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)
@AutoConfigureMockMvc
class ProductControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    ProductService productService;

    @Test
    @DisplayName("GET - 상품 목록")
    void getProducts() throws Exception {
        given(productService.getAllProduct()).willReturn(createProductList());
        mockMvc.perform(get("/v1/products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].id").exists())
                .andExpect(jsonPath("$.data[1].id").exists())
                .andExpect(jsonPath("$.data[2].id").exists())
                .andExpect(jsonPath("$.data[3].id").exists())
                .andExpect(jsonPath("$.data[4].id").exists())
                .andDo(print());
    }

    @Test
    @DisplayName("GET - 상품 상세")
    void getProduct() throws Exception {
        given(productService.getProduct(anyLong())).willReturn(
                Product.createProduct(1L, "productA", 100, "productA is ..."));

        mockMvc.perform(get("/v1/products/" + 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.name").exists())
                .andExpect(jsonPath("$.price").exists())
                .andExpect(jsonPath("$.contents").exists())
                .andDo(print());
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