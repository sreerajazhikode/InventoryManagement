package com.inventory.mangement.model;

import java.util.List;

import lombok.Data;

@Data
public class ProductOut {
    private String productId;
    private Double originalPrice;
    private Double finalPrice;
    private List<String> appliedDiscounts;
    private Double totalSavings;
}
