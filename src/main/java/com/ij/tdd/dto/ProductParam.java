package com.ij.tdd.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ProductParam {

    private String name;
    private String contents;
    private int price;


    public ProductParam(String name, String contents, int price) {
        this.name = name;
        this.contents = contents;
        this.price = price;
    }
}
