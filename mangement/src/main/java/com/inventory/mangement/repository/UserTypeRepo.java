package com.inventory.mangement.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.inventory.mangement.entity.UserType;
@Repository
public interface UserTypeRepo extends CrudRepository<UserType, String> {

}
