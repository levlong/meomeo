package com.test.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.demo.entity.Appointment;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    
}
