/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.jetee.jcache;

import javax.cache.Cache;
import javax.cache.Caching;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.logging.Logger;

/**
 * @author Josue
 */
public class CacheListener implements ServletContextListener {

    public static final String CACHE_NAME = "default-cache";

    private static final Logger logger = Logger.getLogger(CacheListener.class.getName());

    private CachingProvider cachingProvider;
    private Cache<String, Object> cache;

    @Produces
    @ApplicationScoped
    public Cache<String, Object> produceCache() {
        return cache;
    }

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        logger.info("******* INITIALIZING JCACHE *******");

        cachingProvider = Caching.getCachingProvider();
        cache = cachingProvider.getCacheManager().createCache(CACHE_NAME, new MutableConfiguration<String, Object>());
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        logger.info("******* SHUTTING DOWN JCACHE *******");
        cache.close();
        cachingProvider.close();
    }
}
