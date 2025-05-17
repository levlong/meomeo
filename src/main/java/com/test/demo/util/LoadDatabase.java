package com.test.demo.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.test.demo.entity.Dog;
import com.test.demo.repository.DogRepository;

@Configuration
public class LoadDatabase {
    
    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(DogRepository repository) {

        return args -> {
            log.info("Initializing DB ..." + repository.save(new Dog(null, "Baki", "Husky", "Coffee", 7)));
            log.info("Initializing DB ..." + repository.save(new Dog(null, "Yasuo", "Corgi", "Honey Orange", 9)));
        };
    }
}
