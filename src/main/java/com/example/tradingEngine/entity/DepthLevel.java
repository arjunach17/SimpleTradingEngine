package com.example.tradingEngine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DepthLevel {
    //Depth level is nothing but the cummilative value of bid/ask in orderbook
    private String type;
    private Double quantity;
}
