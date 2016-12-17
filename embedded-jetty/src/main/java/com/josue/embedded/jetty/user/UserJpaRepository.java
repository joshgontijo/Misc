package com.josue.embedded.jetty.user;

import com.josue.embedded.jetty.tx.UseTransaction;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Josue on 30/11/2016.
 */
@ApplicationScoped
public class UserJpaRepository {

    private static final Logger logger = Logger.getLogger(UserJpaRepository.class.getName());

    @PostConstruct
    public void init() {
        logger.info("UserJpaRepository -> @PostConstruct");
    }

    @Inject
    private EntityManager em;

    @UseTransaction
    public User create(User user) {
        em.persist(user);
        return user;
    }

    public User getById(String id) {
        return em.find(User.class, id);
    }

    public List<User> getAll() {
        return (List<User>) em.createQuery("SELECT u FROM User u").getResultList();
    }


}
