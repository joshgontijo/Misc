package com.josue.jetee.jcache.interceptor;

import org.jsr107.ri.annotations.cdi.CacheResultInterceptor;

import javax.annotation.Priority;
import javax.cache.annotation.CacheResult;
import javax.interceptor.Interceptor;

/**
 * Created by Josue on 01/01/2017.
 */
@Interceptor
@CacheResult
@Priority(Interceptor.Priority.APPLICATION)
public class EnabledCacheResultInterceptor extends CacheResultInterceptor {

}
