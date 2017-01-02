package com.josue.jetee.example.user;


import com.josue.jetee.jpa.JpaRepository;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

/**
 * Created by Josue on 30/11/2016.
 */
@ApplicationScoped
public class UserJpaRepository extends JpaRepository<User> {

    private static final Logger logger = Logger.getLogger(UserJpaRepository.class.getName());

    public UserJpaRepository() {
        super(User.class);
    }

    @PostConstruct
    public void init() {
        logger.info("UserJpaRepository -> @PostConstruct");
    }

}
