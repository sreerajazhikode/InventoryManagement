package com.inventory.mangement.entity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Products {
    @Id
    @Column(name = "id")
    private String productId;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "base_price")
    private Double basePrice;
    @Column(name = "category")
    private String category;
    @Column(name = "available_quantity")
    private Integer availableQuantity;
}
