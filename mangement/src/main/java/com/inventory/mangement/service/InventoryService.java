package com.inventory.mangement.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.mangement.entity.Products;
import com.inventory.mangement.entity.PromoCode;
import com.inventory.mangement.entity.QuantityDiscounts;
import com.inventory.mangement.entity.UserType;
import com.inventory.mangement.model.Discount;
import com.inventory.mangement.model.ProductReq;
import com.inventory.mangement.model.ProductResp;
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

    List<Discount> appliedDiscount= new ArrayList<>();

    public ProductResp calculateDiscount(ProductReq productInfo) throws Exception{
        appliedDiscount.clear();
        Double discountPricePromoCode=0.0, discountPriceUserType=0.0,discountPriceMinQuantity=0.0 ;
        
        Products product = productRepository.findById(productInfo.getProductId()).orElseThrow(()->new Exception("product Not Found"));

        if(productInfo.getQuantity() > product.getAvailableQuantity()){
            throw new Exception("Quantity not available, Entered quantity is more than the available quantity");
        } else {
            if(Objects.nonNull(productInfo.getPromoCode())){
                discountPricePromoCode = applyPromoCode(product, productInfo.getPromoCode());
            }  

            if(Objects.nonNull(productInfo.getUserType())){
                discountPriceUserType = applyUserTypeDiscount( product, productInfo.getUserType());
            }

            if(productInfo.getQuantity()>1){
                discountPriceMinQuantity =  applyMinQuantityDiscount( product, productInfo.getQuantity());
            }

            product.setAvailableQuantity(product.getAvailableQuantity()-productInfo.getQuantity());
            productRepository.save(product);
        }

        ProductResp productOut=new ProductResp();
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
        if(promoCodes != null && promoCodes.isActive() && promoCodes.getValidUntil().isAfter(LocalDate.now())){
            price = product.getBasePrice()* promoCodes.getDiscountPercentage()/100;
            Discount discount = new Discount();
            discount.setType("PROMO_CODE");
            discount.setPercentage(promoCodes.getDiscountPercentage());
            appliedDiscount.add(discount);
        }
        return price;
    }

    private Double applyUserTypeDiscount(Products product, String userType) throws Exception{
        UserType userTypes = userTypeRepo.findById(userType).orElseThrow(()->new Exception("UserType Not Fouund"));
        Double finalPrice=0.0;
        if(userTypes != null){
            finalPrice = product.getBasePrice()* userTypes.getDiscountPercentage()/100;
            Discount discount = new Discount();
            discount.setType(userTypes.getType());
            discount.setPercentage(userTypes.getDiscountPercentage());
            appliedDiscount.add(discount);
        }
        return finalPrice;
    }

    private Double applyMinQuantityDiscount( Products product, Integer quantity) throws Exception{
        Iterable<QuantityDiscounts> quantityDiscounts =  minQuantityRepo.findAll();
        Double discountPercentage=0.0;
        Integer existingQuantity = 0;
        for(QuantityDiscounts quantityDiscount : quantityDiscounts){
            if(quantityDiscount.getMinQuantity() > existingQuantity && quantity >= quantityDiscount.getMinQuantity()){
                discountPercentage = quantityDiscount.getDiscountPercentage();
            }
            existingQuantity = quantityDiscount.getMinQuantity();
        }
        Discount discount = new Discount();
        discount.setType("MIN_QUANTITY");
        discount.setPercentage(discountPercentage);
        appliedDiscount.add(discount);
    
        return (product.getBasePrice()* discountPercentage/100);
    }

}
