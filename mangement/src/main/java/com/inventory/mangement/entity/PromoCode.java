package com.inventory.mangement.entity;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "promo_codes")
public class PromoCode {
    @Id
    private String code;
    private double discountPercentage;
    private boolean active;
    private LocalDate validUntil;
}
