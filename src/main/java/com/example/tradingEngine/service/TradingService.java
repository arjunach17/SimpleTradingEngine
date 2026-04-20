package com.example.tradingEngine.service;

import com.example.tradingEngine.entity.Orders;
import com.example.tradingEngine.entity.User;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicLookAndFeel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TradingService {

        private List<User> users = new ArrayList<>();
        private List<Orders> bids =new ArrayList<>();
        private List<Orders> asks =new ArrayList<>();

        public TradingService(){

        //Initializing in memory values
        Map<String,Double> balance1=new HashMap<>();

        balance1.put("GOOGLE",10.0);
        balance1.put("USD",10000.0);
        users.add(new User("1",balance1));

        Map<String,Double> balance2=new HashMap<>();
        balance2.put("GOOGLE",10.0);
        balance2.put("USD",10000.0);
        users.add(new User("2",balance2));
        }



}
