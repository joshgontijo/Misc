package com.josue.jetee.jpa;

import com.josue.jetee.tx.UseTransaction;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Id;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.lang.reflect.Field;
import java.util.List;

/**
 * Created by Josue on 01/01/2017.
 */
public abstract class JpaRepository<T> {

    @Inject
    private EntityManager em;

    private String idFieldName;
    private final Class<T> clazz;

    public JpaRepository(Class<T> clazz) {
        this.clazz = clazz;
        readIdField();
    }

    @UseTransaction
    public T create(T entity) {
        em.persist(entity);
        return entity;
    }

    @UseTransaction
    public T update(T entity) {
        return em.merge(entity);
    }

    @UseTransaction
    public void delete(T entity) {
        em.remove(em.merge(entity));
    }

    public T find(Object id) {
        return em.find(clazz, id);
    }

    public List<T> getAll() {
        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = qb.createQuery(clazz);
        cq.from(clazz);
//        cq.where();
        return em.createQuery(cq).getResultList();
    }

    public long count() {
        readIdField();

        CriteriaBuilder qb = em.getCriteriaBuilder();
        CriteriaQuery<Long> cq = qb.createQuery(Long.class);
        cq.select(qb.count(cq.from(clazz)));
//        cq.where();
        return em.createQuery(cq).getSingleResult();
    }

    private void readIdField() {
        if (idFieldName != null) {
            return;
        }
        for (Field f : clazz.getDeclaredFields()) {
            f.setAccessible(true);
            Id idAnnotation = f.getAnnotation(Id.class);
            if (idAnnotation != null) {
                idFieldName = f.getName();
                return;
            }
        }
        throw new RuntimeException("No field annotated with @Id has been found");
    }
}
