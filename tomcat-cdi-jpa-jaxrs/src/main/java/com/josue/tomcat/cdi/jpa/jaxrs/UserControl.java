/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.tomcat.cdi.jpa.jaxrs;

import com.josue.tomcat.cdi.jpa.jaxrs.tx.UseTransaction;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author Josue
 */
public class UserControl {

    @Inject
    private EntityManager em;

    public List<User> getUsers() {
        TypedQuery<User> query = em.createQuery("SELECT u FROM User u", User.class);
        List<User> foundUsers = query.getResultList();
        return foundUsers;
    }

    public User getUser(String id) {
        return em.find(User.class, id);
    }

    @UseTransaction
    public User createUser(User user) {
        em.persist(user);
        return user;
    }

}
