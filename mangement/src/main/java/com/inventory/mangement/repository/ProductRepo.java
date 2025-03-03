package com.inventory.mangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.inventory.mangement.model.Products;
@Repository
public interface ProductRepo extends CrudRepository<Products, String> {

}
