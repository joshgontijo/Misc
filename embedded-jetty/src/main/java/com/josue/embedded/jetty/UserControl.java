package com.josue.embedded.jetty;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import java.util.logging.Logger;

/**
 * Created by Josue on 30/11/2016.
 */
@ApplicationScoped
public class UserControl {

    private static final Logger logger = Logger.getLogger(UserControl.class.getName());

    @PostConstruct
    public void init() {
        logger.info("UserControl -> @PostConstruct");
    }

    public User getUser(){
        return new User("Josh", 27);
    }


}
