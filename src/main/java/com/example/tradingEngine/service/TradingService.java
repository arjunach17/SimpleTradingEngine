package com.example.tradingEngine.service;

import com.example.tradingEngine.dto.OrderRequest;
import com.example.tradingEngine.entity.DepthLevel;
import com.example.tradingEngine.entity.Orders;
import com.example.tradingEngine.entity.User;
import org.springframework.stereotype.Service;

import javax.swing.plaf.basic.BasicLookAndFeel;
import java.util.*;

@Service
public class TradingService {
        public static final String TICKER = "GOOGLE";
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

        public Map<String,Double> placeOrder(OrderRequest req){

                double remainingquantity=fillOrder(req.getPrice(),req.getQuantity(),req.getSide(),req.getUserId());

                if(remainingquantity ==0){
                    return Map.of("filled quantity", req.getQuantity());
                }

                if("bid".equals(req.getSide())){
                    bids.add(
                            Orders.builder()
                                    .userId(req.getUserId())
                                    .price(req.getPrice())
                                    .quantity(remainingquantity)
                                    .build());
                    bids.sort(Comparator.comparingDouble(Orders::getPrice));
                }
                else{
                    asks.add(
                            Orders.builder()
                                    .userId(req.getUserId())
                                    .price(req.getPrice())
                                    .quantity(req.getQuantity())
                                    .build());
                    asks.sort((a, b) -> Double.compare(b.getPrice(), a.getPrice()));
                }

            return Map.of("filledquantity",req.getQuantity()-remainingquantity);
        }

            public double fillOrder(double price, double quantity,String side,String userId){
                    double remainingQuantity=quantity;
                    if("bid".equals(side)){
                        for(int i=asks.size()-1;i>=0;i--){
                            Orders ask =asks.get(i);
                            if(ask.getPrice()>price){
                                break;
                            }
                            if(ask.getQuantity() > remainingQuantity){
                                ask.setQuantity(ask.getQuantity() - remainingQuantity);
                                flipBalance(userId,ask.getUserId(),remainingQuantity,ask.getPrice());
                                return 0;
                            }
                            else{
                                remainingQuantity-= ask.getQuantity();
                                flipBalance(userId,ask.getUserId(),ask.getQuantity(),ask.getPrice());
                                asks.remove(i);
                            }

                        }
                    }
                    else{
                        for(int i=bids.size()-1;i>=0;i--){
                            Orders bid=bids.get(i);
                            if(bid.getPrice()<price){
                                break;
                            }
                            if(bid.getQuantity()>remainingQuantity){
                                bid.setQuantity(bid.getQuantity()-remainingQuantity);
                                flipBalance(userId,bid.getUserId(),remainingQuantity,bid.getPrice());
                                return 0;
                            }
                            else{
                                remainingQuantity-= bid.getQuantity();
                                flipBalance(userId,bid.getUserId(),bid.getQuantity(),bid.getPrice());
                                bids.remove(i);
                            }
                        }
                    }
                    return remainingQuantity;
            }
            private void flipBalance(String userId1, String userId2, double quantity, double price) {
                User user1 = users.stream().filter(u -> u.getUserId().equals(userId1)).findFirst().orElse(null);
                User user2 = users.stream().filter(u -> u.getUserId().equals(userId2)).findFirst().orElse(null);

                if (user1 == null || user2 == null) {
                    return;
                }

                user1.getBalances().put(TICKER, user1.getBalances().get(TICKER) - quantity);
                user2.getBalances().put(TICKER, user2.getBalances().get(TICKER) + quantity);

                user1.getBalances().put("USD", user1.getBalances().get("USD") + (quantity * price));
                user2.getBalances().put("USD", user2.getBalances().get("USD") - (quantity * price));
            }

            public Map<String, Map<String, DepthLevel>> getDepth() {
                Map<String, DepthLevel> depth = new HashMap<>();

                for (Orders bid : bids) {
                    String priceKey = String.valueOf(bid.getPrice());
                    depth.computeIfAbsent(priceKey, k -> new DepthLevel("bid", 0.0))
                            .setQuantity(depth.get(priceKey).getQuantity() + bid.getQuantity());
                }

                for (Orders ask : asks) {
                    String priceKey = String.valueOf(ask.getPrice());
                    depth.computeIfAbsent(priceKey, k -> new DepthLevel("ask", 0.0))
                            .setQuantity(depth.get(priceKey).getQuantity() + ask.getQuantity());
                }

                return Map.of("depth", depth);
            }

            public Map<String, Object> getBalance(String userId) {
                User user = users.stream().filter(u -> u.getUserId().equals(userId)).findFirst().orElse(null);
                if (user == null) {
                    return Map.of("USD", 0, TICKER, 0);
                }
                return Map.of("balances", user.getBalances());
            }
        }

