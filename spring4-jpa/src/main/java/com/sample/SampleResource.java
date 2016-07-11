package com.sample;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author Josue Gontijo <josue.gontijo@maersk.com>.
 */
@Transactional
@Repository
@RestController
public class SampleResource {

    @PersistenceContext
    private EntityManager em;

    @ResponseBody
    @RequestMapping(value = "/sample", method = RequestMethod.GET)
    public User hello() {
        em.persist(new User(27, "josh"));
        return new User(27, "josh");
    }

}
