package com.inventory.mangement.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.inventory.mangement.model.ProductInfo;
import com.inventory.mangement.model.ProductOut;
import com.inventory.mangement.service.InventoryService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/products")
public class InventoryController {
    @Autowired
    InventoryService inventoryService;
    @PostMapping("/price-calculation")
    public ResponseEntity<?> postMethodName(@RequestBody ProductInfo productInfo) throws Exception{
        try{
            validateRequestBody(productInfo);
            ProductOut productOut = inventoryService.calculateDiscount(productInfo);
            return new ResponseEntity<>(productOut, HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Bad Request", HttpStatus.BAD_REQUEST);
        }
    }

    private void validateRequestBody(ProductInfo productInfo) throws Exception{
        if(productInfo.getQuantity()<1)
            throw new Exception("Quantity Should be greated Than 0");
        else if (productInfo.getProductId() == null)
            throw new Exception("Product Id Shouldn't be Null");
    }

}
