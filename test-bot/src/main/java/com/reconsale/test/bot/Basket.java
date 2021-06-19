package com.reconsale.test.bot;

import java.util.HashSet;
import java.util.Set;

import lombok.Data;

@Data
public class Basket {

    private Set<Product> products = new HashSet<Product>();
    
    public void clear() {
        products.clear();
    }
    
    public void add(Product product) {
        products.add(product);
    }
    
}
