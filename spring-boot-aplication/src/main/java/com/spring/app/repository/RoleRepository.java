package com.spring.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.spring.app.entity.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long>{

}
