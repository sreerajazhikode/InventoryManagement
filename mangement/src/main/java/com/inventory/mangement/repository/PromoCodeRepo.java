package com.inventory.mangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.inventory.mangement.model.PromoCode;
@Repository
public interface PromoCodeRepo extends CrudRepository<PromoCode, String>{

}
