package com.inventory.mangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.inventory.mangement.entity.Products;
@Repository
public interface ProductRepo extends CrudRepository<Products, String> {

}
