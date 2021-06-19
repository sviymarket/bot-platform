package com.reconsale.test.bot;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Product {

    private String name;
    
    private String image;
    
    private String price;
}
