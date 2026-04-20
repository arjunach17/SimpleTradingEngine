package com.example.tradingEngine.entity;

import lombok.Data;

@Data
public class DepthLevel {
    //Depth level is nothing but the cummilative value of bid/ask in orderbook
    private String type;
    private Double quantity;
}
