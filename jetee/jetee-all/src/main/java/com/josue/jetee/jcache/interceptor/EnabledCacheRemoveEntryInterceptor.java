package com.josue.jetee.jcache.interceptor;

import org.jsr107.ri.annotations.cdi.CachePutInterceptor;
import org.jsr107.ri.annotations.cdi.CacheRemoveEntryInterceptor;

import javax.annotation.Priority;
import javax.cache.annotation.CachePut;
import javax.cache.annotation.CacheRemove;
import javax.interceptor.Interceptor;

/**
 * Created by Josue on 01/01/2017.
 */
@Interceptor
@CacheRemove
@Priority(Interceptor.Priority.APPLICATION)
public class EnabledCacheRemoveEntryInterceptor extends CacheRemoveEntryInterceptor {

}
