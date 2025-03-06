package com.inventory.mangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.inventory.mangement.entity.QuantityDiscounts;
@Repository
public interface MinQuantityRepo extends CrudRepository<QuantityDiscounts, Integer>{

}
