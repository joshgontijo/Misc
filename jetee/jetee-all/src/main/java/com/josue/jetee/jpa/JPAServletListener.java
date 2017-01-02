/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.jetee.jpa;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.enterprise.context.Dependent;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.josue.jetee.JettyServer.PERSISTENCE_XML_LOCATION;

/**
 * @author Josue
 */
public class JPAServletListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(JPAServletListener.class.getName());

    //should be static
    private static EntityManagerFactory factory;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("********* INITIALIZING ENTITY MANAGER **********");
        List<String> units = getPersistenceUnits();
        factory = Persistence.createEntityManagerFactory(units.get(0)); //TODO single unit is supported
    }

    private List<String> getPersistenceUnits() {
        URL persistence = Thread.currentThread().getContextClassLoader().getResource(PERSISTENCE_XML_LOCATION);
        if (persistence == null) {
            throw new IllegalStateException("Couldn't find " + PERSISTENCE_XML_LOCATION);
        }

        List<String> persistenceUnits = new ArrayList<>();
        try {
            DocumentBuilderFactory dbFactory
                    = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(persistence.toString());

            NodeList units = doc.getElementsByTagName("persistence-unit");
            for (int temp = 0; temp < units.getLength(); temp++) {
                Element eElement = (Element) units.item(temp);
                String name = eElement.getAttribute("name");
                logger.info("********* FOUND PERSISTNCE UNIT: '" + name + "' **********");
                persistenceUnits.add(name);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (persistenceUnits.size() > 1) {
            logger.warning("********* WARNING: ONLY ONE PERSISTENCE UNIT IS SUPPORTED AT THE MOMENT, USING FIRST *********");
        }

        return persistenceUnits;
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        if (factory != null) {
            logger.info("********* SHUTTING DOWN ENTITY MANAGER **********");
            factory.close();
        }
    }

    @Produces
    @Dependent
    public EntityManager emProducer() {
        if (factory == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
        return factory.createEntityManager();
    }
}
