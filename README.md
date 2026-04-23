
***
# 📈 Mini Trading Engine

A super simple, lightweight, in-memory order book built with Java and Spring Boot. 

I built this to simulate how a real stock exchange or crypto exchange matches buy (bids) and sell (asks) orders. It handles full fills, partial fills, and keeps track of user balances—all without needing a clunky database setup!

##  What does it do?
* **Limit Orders:** Place exact price buy/sell orders.
* **Order Matching:** Automatically matches buyers and sellers and adjusts their balances.
* **Market Depth:** See the aggregated total of all open orders at every price level.
* **Zero Setup:** Data is stored in-memory using standard Java data structures. Just hit "Run" and you're good to go.

## What you need to run it
* Java 17 (or higher)
* Maven

##  How to start
Since there's no database to configure, running this is incredibly easy.

1. Clone the repo.
2. Open your terminal and run:
   ```bash
   mvn spring-boot:run
   ```
3. The server will start up on `http://localhost:8080`.

*(Alternatively, just open the project in IntelliJ or Eclipse and click the big green Play button on the main application class!)*

---

##  Playing with the API

*Note: To make testing easy, the engine starts up with two pre-funded users: **User "1"** and **User "2"**. They both start with 10 GOOGLE shares and $10,000 USD.*

### 1. Place an Order
**`POST /order`**

Tell the engine what you want to do. Here is an example of User 1 placing a "bid" (buy) order for 5 shares at $100:

**Request Body:**
```json
{
  "userId": "1",
  "side": "bid",
  "price": 100.0,
  "quantity": 5.0
}
```

### 2. Check the Order Book (Market Depth)
**`GET /depth`**

Want to see all the open orders waiting to be filled? This endpoint sums up all the bids and asks so you can see exactly what the market looks like right now.

**Example Response:**
```json
{
  "depth": {
    "100.0": {
      "type": "bid",
      "quantity": 5.0
    }
  }
}
```

### 3. Check a User's Balance
**`GET /balance/{userId}`**

After some trades happen, you can check how much money or stock a user has left. 

**Example Request:** `GET /balance/1`

**Example Response:**
```json
{
  "balances": {
    "GOOGLE": 15.0,
    "USD": 9500.0
  }
}
```

---

##  A Quick Heads Up
Because this is an **in-memory** application, the database lives in your RAM. If you stop the server, all your trades, open orders, and balance changes will be wiped clean. Every time you restart, the app will give User 1 and User 2 their starting money back!
