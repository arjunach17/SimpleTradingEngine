package com.example.tradingEngine.entity;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Orders {

    private String userId;
    private Double price;
    private Double quantity;

}
