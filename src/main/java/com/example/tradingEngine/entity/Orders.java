package com.example.tradingEngine.entity;


import lombok.Data;

@Data
public class Orders {

    private String userId;
    private Double price;
    private Double quantity;

}
