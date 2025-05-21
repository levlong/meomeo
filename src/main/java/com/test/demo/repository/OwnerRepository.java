package com.test.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.demo.entity.Owner;

public interface OwnerRepository extends JpaRepository<Owner, Long>{
    
}
