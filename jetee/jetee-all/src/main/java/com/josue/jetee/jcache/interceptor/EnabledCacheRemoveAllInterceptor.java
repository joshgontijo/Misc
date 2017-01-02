package com.josue.jetee.jcache.interceptor;

import org.jsr107.ri.annotations.cdi.CacheRemoveAllInterceptor;

import javax.annotation.Priority;
import javax.cache.annotation.CacheRemoveAll;
import javax.interceptor.Interceptor;

/**
 * Created by Josue on 01/01/2017.
 */
@Interceptor
@CacheRemoveAll
@Priority(Interceptor.Priority.APPLICATION)
public class EnabledCacheRemoveAllInterceptor extends CacheRemoveAllInterceptor {

}
