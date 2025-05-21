package com.test.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.demo.entity.Appointment;
import com.test.demo.entity.Dog;
import com.test.demo.entity.Owner;
import com.test.demo.entity.enums.AppointmentStatus;
import com.test.demo.repository.AppointmentRepository;
import com.test.demo.repository.DogRepository;
import com.test.demo.repository.OwnerRepository;

@Configuration
public class LoadDatabase {
    
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final DogRepository dogRepository;
    private final OwnerRepository ownerRepository;
    private final AppointmentRepository appointmentRepository;
    
    public LoadDatabase(DogRepository dogRepository, OwnerRepository ownerRepository, AppointmentRepository appointmentRepository) {
        this.dogRepository = dogRepository;
        this.ownerRepository = ownerRepository;
        this.appointmentRepository = appointmentRepository;
    }
    @Bean
    CommandLineRunner initDatabase() {

        return args -> {
            Owner owner = new Owner(null, "Long", "Da Nang", null, null);
            log.info("Adding Owner to DB ...", ownerRepository.save(owner));

            Dog dog = new Dog(null, "Gaga", "Husky", "Brown", 5, owner, null);
            log.info("Adding Dog to DB ...", dogRepository.save(dog));
            
            Appointment appointment = new Appointment(null, "No eating", AppointmentStatus.SCHEDULED, owner, dog);
            appointmentRepository.save(appointment);
            log.info(owner.getName() + " is " + appointment.getStatus().toString() + " an appointment for " + dog.getName());
        };
    }
}
