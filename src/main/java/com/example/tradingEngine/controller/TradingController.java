package com.example.tradingEngine.controller;

import com.example.tradingEngine.dto.OrderRequest;
import com.example.tradingEngine.entity.DepthLevel;
import com.example.tradingEngine.service.TradingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/")
public class TradingController {

        @Autowired
        private TradingService tradingService;

        @PostMapping("order")
        public Map<String,Double> placeOrder(@RequestBody OrderRequest req){
            return tradingService.placeOrder(req);
        }

        @GetMapping("depth")
        public Map<String,Map<String, DepthLevel>>getDepth(){
            return tradingService.getDepth();
        }

        @GetMapping("balance/{userId}")
        public Map<String,Object> getBalance(@PathVariable String userId){
           return tradingService.getBalance(userId);
        }

}
