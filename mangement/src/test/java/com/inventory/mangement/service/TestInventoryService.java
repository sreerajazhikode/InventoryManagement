package com.inventory.mangement.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import com.inventory.mangement.model.ProductInfo;
import com.inventory.mangement.model.ProductOut;
import com.inventory.mangement.model.Products;
import com.inventory.mangement.repository.MinQuantityRepo;
import com.inventory.mangement.repository.ProductRepo;
import com.inventory.mangement.repository.PromoCodeRepo;
import com.inventory.mangement.repository.UserTypeRepo;

@SpringBootTest
public class TestInventoryService {
    @InjectMocks
    InventoryService inventoryService;
    @Mock
    ProductRepo productRepository;
    @Mock
    PromoCodeRepo promoCodeRepo;
    @Mock
    MinQuantityRepo minQuantityRepo;
    @Mock
    UserTypeRepo userTypeRepo;

    @Test
    void testCalculateDiscount() throws Exception{
        ProductInfo prodInfo = new ProductInfo();
        prodInfo.setProductId("ABC123");
        prodInfo.setPromoCode("SPRING25");
        prodInfo.setQuantity(5);
        prodInfo.setUserType("PREMIUM");
        when(productRepository.findById(Mockito.anyString())).thenReturn(product());
        assertThrows(Exception.class,()-> inventoryService.calculateDiscount(prodInfo));
    }

    Optional<Products> product(){
        Products product = new Products();
        product.setAvailableQuantity(12);
        product.setBasePrice(12.12);
        product.setCategory("PREMIUM");
        product.setProductId("ABC123");
        return Optional.of(product);
    }
}
