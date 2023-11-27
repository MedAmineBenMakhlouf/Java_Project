package com.artisana.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.artisana.models.Role;

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
	
    List<Role> findAll();
    
    Role findByName(String name);
}