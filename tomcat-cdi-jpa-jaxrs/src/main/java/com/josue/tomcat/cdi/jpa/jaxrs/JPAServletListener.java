/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.tomcat.cdi.jpa.jaxrs;

import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author Josue
 */
@WebListener
public class JPAServletListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(JPAServletListener.class.getName());

    //should be static
    private static EntityManagerFactory factory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("********* INITIALIZING ENTITYMANAGER **********");
        factory = Persistence.createEntityManagerFactory("MY-PU");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (factory != null) {
            factory.close();
        }
    }

    @Produces
    @RequestScoped // dont use dependant scope with interceptors
    public EntityManager emProducer() {
        if (factory == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        return factory.createEntityManager();
    }
}
