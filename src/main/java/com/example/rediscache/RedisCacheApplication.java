package com.example.rediscache;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@Slf4j
@SpringBootApplication
@EnableCaching
public class RedisCacheApplication implements CommandLineRunner {

    private final Logger logger = LoggerFactory.getLogger(RedisCacheApplication.class);

    @Autowired
    private UserRepository userRepository;

    public static void main(String[] args) {

        SpringApplication.run(RedisCacheApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Saving users. Current user count is {}." + userRepository.count());
        User shubham = new User("Shubham", 2000);
        User pankaj = new User("Pankaj", 29000);
        User lewis = new User("Lewis", 550);

        userRepository.save(shubham);
        userRepository.save(pankaj);
        userRepository.save(lewis);
        logger.info("Done saving users. Data: {}.", userRepository.findAll());
    }


}
