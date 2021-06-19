package com.reconsale.test.bot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.reconsale.bot.model.Response;

@SpringBootApplication
@ComponentScan(basePackages = {"com.reconsale"})

public abstract class Application {
    
    public static final Map<String, Response> lastResponses = new HashMap<String, Response>();
    public static final List<Product> products = new ArrayList<Product>();
    public static final Map<String, Basket> baskets = new HashMap<String, Basket>();
    
    static {
        products.add(new Product("Cilit Bank", "https://content.rozetka.com.ua/goods/images/big/10717184.jpg", "150"));
        products.add(new Product("Colgate", "https://content2.rozetka.com.ua/goods/images/big/11011825.jpg", "75"));
        products.add(new Product("Ms. Proper", "https://content2.rozetka.com.ua/goods/images/big/10689731.png", "45"));    
        products.add(new Product("Domestos", "https://content.rozetka.com.ua/goods/images/big/10693614.jpg", "63"));
        products.add(new Product("Fairy Prof", "https://content1.rozetka.com.ua/goods/images/big/11608678.jpg", "46"));    
        products.add(new Product("Cif", "https://content1.rozetka.com.ua/goods/images/big/73884129.jpg", "41"));
        products.add(new Product("Pronto", "https://content1.rozetka.com.ua/goods/images/big/160585555.jpg", "101"));
    }
    
    public static Product getProduct(String name) {
        return products.stream().filter(p -> p.getName().equals(name)).findAny().get();
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
