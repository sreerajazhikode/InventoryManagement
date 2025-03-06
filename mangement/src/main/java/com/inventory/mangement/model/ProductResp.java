package com.inventory.mangement.model;

import java.util.List;

import lombok.Data;

@Data
public class ProductResp {
    private String productId;
    private Double originalPrice;
    private Double finalPrice;
    private List<Discount> appliedDiscounts;
    private Double totalSavings;
}
