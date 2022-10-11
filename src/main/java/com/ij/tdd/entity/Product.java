package com.ij.tdd.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private int price;
    private String contents;

    protected Product(Long id, String name, int price, String contents) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.contents = contents;
    }

    public static Product createProduct(String name, int price, String contents) {
        return new Product(null, name, price, contents);
    }

    public static Product createProduct(Long id, String name, int price, String contents) {
        return new Product(id, name, price, contents);
    }

    public void changeProduct(String name, int price, String contents) {
        this.name = name;
        this.price = price;
        this.contents = contents;
    }
}
