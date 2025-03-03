package com.inventory.mangement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.mangement.model.Products;
import com.inventory.mangement.model.ProductInfo;
import com.inventory.mangement.model.ProductOut;
import com.inventory.mangement.model.PromoCode;
import com.inventory.mangement.model.QuantityDiscounts;
import com.inventory.mangement.model.UserType;
import com.inventory.mangement.repository.MinQuantityRepo;
import com.inventory.mangement.repository.ProductRepo;
import com.inventory.mangement.repository.PromoCodeRepo;
import com.inventory.mangement.repository.UserTypeRepo;

@Service
public class InventoryService {
    @Autowired
    ProductRepo productRepository;
    @Autowired
    PromoCodeRepo promoCodeRepo;
    @Autowired
    MinQuantityRepo minQuantityRepo;
    @Autowired
    UserTypeRepo userTypeRepo;

    List<String> appliedDiscount= new ArrayList<>();

    public ProductOut calculateDiscount(ProductInfo productInfo) throws Exception{
        Double discountPricePromoCode=0.0, discountPriceUserType=0.0,discountPriceMinQuantity=0.0 ;
        
        Products product = productRepository.findById(productInfo.getProductId()).orElseThrow(()->new Exception("productNotFound"));

        if(productInfo.getQuantity() > product.getAvailableQuantity()){
            throw new Exception("Quantity not available");
        } else {
            if(!productInfo.getPromoCode().isBlank()){
                discountPricePromoCode = applyPromoCode(product, productInfo.getPromoCode());
            } 

            discountPriceUserType = applyUserTypeDiscount( product, productInfo.getUserType());

            if(productInfo.getQuantity()>1){
                discountPriceMinQuantity =  applyMinQuantityDiscount( product, productInfo.getQuantity());
            }

            product.setAvailableQuantity(product.getAvailableQuantity()-productInfo.getQuantity());
            productRepository.save(product);
        }
        ProductOut productOut=new ProductOut();
        productOut.setOriginalPrice(product.getBasePrice());
        productOut.setFinalPrice(product.getBasePrice() - discountPricePromoCode - discountPriceMinQuantity - discountPriceUserType);
        productOut.setProductId(productInfo.getProductId());
        productOut.setTotalSavings(discountPricePromoCode + discountPriceMinQuantity + discountPriceUserType);
        productOut.setAppliedDiscounts(appliedDiscount);
            
        return productOut;
    }

    private Double applyPromoCode(Products product, String promoCode) throws Exception{
        PromoCode promoCodes = promoCodeRepo.findById(promoCode).orElseThrow(()->new Exception("PromoCode Not Fouund"));
        Double price=0.0;
        if(promoCodes != null && promoCodes.isActive() && promoCodes.getValidUntil().isBefore(LocalDate.now())){
            price = product.getBasePrice()* promoCodes.getDiscountPercentage()/100;
            appliedDiscount.add("{\"type\": \"PROMO_CODE\", \"percentage\":"+promoCodes.getDiscountPercentage());
        }
        return price;
    }

    private Double applyUserTypeDiscount(Products product, String userType) throws Exception{
        UserType userTypes = userTypeRepo.findById(userType).orElseThrow(()->new Exception("UserType Not Fouund"));
        Double finalPrice=0.0;
        if(userTypes != null){
            finalPrice = product.getBasePrice()* userTypes.getDiscountPercentage()/100;
            appliedDiscount.add("{\"type\": "+userTypes.getType() +", \"percentage\":"+userTypes.getDiscountPercentage());
        }
        return finalPrice;
    }

    private Double applyMinQuantityDiscount( Products product, Integer quantity) throws Exception{
        Iterable<QuantityDiscounts> quantityDiscounts =  minQuantityRepo.findAll();
        Double discountPercentage=0.0;
        Integer existingQuantity = 0;
        for(QuantityDiscounts quantityDiscount : quantityDiscounts){
            if(quantityDiscount.getMinQuantity() > existingQuantity && quantity > quantityDiscount.getMinQuantity()){
                discountPercentage = quantityDiscount.getDiscountPercentage();
            }
            existingQuantity = quantityDiscount.getMinQuantity();
        }
        String stirng = "{\"type\": \"PROMO_CODE\", \"percentage\" \"" + discountPercentage;
        appliedDiscount.add(stirng);
    
        return (product.getBasePrice()* discountPercentage/100);
    }

}
