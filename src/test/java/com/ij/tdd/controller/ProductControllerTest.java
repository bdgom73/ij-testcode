package com.ij.tdd.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ij.tdd.dto.ProductParam;
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
import org.springframework.test.web.servlet.MvcResult;

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

    ObjectMapper objectMapper = new ObjectMapper();

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

    @Test
    @DisplayName("POST - 상품 등록")
    void createProduct() throws Exception {
        ProductParam param
                = new ProductParam(
                        "productA",
                "productA is ...",
                1000);

        String content = objectMapper.writeValueAsString(param);

        mockMvc.perform(
                post("/v1/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("PUT - 상품 수정")
    void updateProduct() throws Exception {
        ProductParam param
                = new ProductParam(
                "updateProductA",
                "productA update!",
                2000);

        String content = objectMapper.writeValueAsString(param);

        mockMvc.perform(
                        put("/v1/products/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(content)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("DELETE - 상품 삭제")
    void deleteProduct() throws Exception {
        mockMvc.perform(delete("/v1/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                
                )
                .andExpect(status().isOk())
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