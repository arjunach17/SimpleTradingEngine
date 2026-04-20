package com.example.tradingEngine.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private String userId;
    private Map<String,Double> balances;
}
