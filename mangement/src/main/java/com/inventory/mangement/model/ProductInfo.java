package com.inventory.mangement.model;

import lombok.Data;

@Data
public class ProductInfo {
    private String productId;
    private Integer quantity;
    private String promoCode;
    private String userType;
}
