package com.example.rediscache;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController("/users")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(UserController.class);

    private UserRepository userRepository;

    @Autowired
    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Cacheable(value = "users", key = "#userId", unless = "#result.followers < 12000", cacheManager = "usersCacheMgr")
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public User getUser(@PathVariable String userId) throws InterruptedException {
        logger.info("Getting user with ID {}.", userId);
        Thread.sleep(5*1000);
        return userRepository.findById(Long.valueOf(userId)).get();
    }

    @CachePut(value = "users", key = "#user.id")
    @PutMapping
    public User updatePersonByID(@RequestBody User user) {
        userRepository.save(user);
        return user;
    }

    @CacheEvict(value = "users", allEntries=true)
    @DeleteMapping("/{id}")
    public void deleteUserByID(@PathVariable Long id) {
        logger.info("deleting person with id {}", id);
        userRepository.deleteById(id);
    }

}
