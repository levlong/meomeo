package com.test.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.demo.entity.Dog;

public interface DogRepository extends JpaRepository<Dog, Long> {

}
