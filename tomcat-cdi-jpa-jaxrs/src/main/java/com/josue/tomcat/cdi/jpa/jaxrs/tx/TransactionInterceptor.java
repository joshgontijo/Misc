/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.tomcat.cdi.jpa.jaxrs.tx;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.persistence.EntityManager;

/**
 *
 * @author Josue
 */
@UseTransaction
@Interceptor
@Priority(Interceptor.Priority.APPLICATION)
public class TransactionInterceptor {

    @Inject
    private EntityManager em;

    @AroundInvoke
    public Object aroundInvoke(InvocationContext ic) throws Exception {
        boolean act = !em.getTransaction().isActive();
        if (act) {
            em.getTransaction().begin();
        }
        try {
            Object result = ic.proceed();
            if (act) {
                em.getTransaction().commit();
            }
            return result;
        } catch (Exception e) {
            if (act) {
                em.getTransaction().rollback();
            }
            throw e;
        }
    }
}
