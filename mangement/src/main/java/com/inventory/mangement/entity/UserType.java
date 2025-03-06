package com.inventory.mangement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "user_types")
public class UserType {
    @Id
    private String type;
    private Double discountPercentage;
}
