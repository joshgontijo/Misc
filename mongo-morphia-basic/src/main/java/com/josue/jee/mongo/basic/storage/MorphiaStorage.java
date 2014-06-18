/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.josue.jee.mongo.basic.storage;

import com.josue.jee.mongo.basic.bean.User;
import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

@Stateless
public class MorphiaStorage {

    private static final Logger LOG = Logger.getLogger(MorphiaStorage.class.getName());

    //not good, but is just a sample
    private Datastore datastore;

    @PostConstruct
    public void init() {
        try {
            MongoClient mongo = new MongoClient("localhost", 27017);

            List<String> dbs = mongo.getDatabaseNames();
            for (String db : dbs) {
                LOG.info(db);
            }

//            DB db = mongo.getDB("simple-mongojack");
//            DBCollection table = db.getCollection("restlogs");
            Morphia morphia = new Morphia();
            morphia.map(User.class);
            datastore = morphia.createDatastore(mongo, "morphia");

        } catch (UnknownHostException ex) {
            Logger.getLogger(MorphiaStorage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public User createUser(User user) {
        Key<User> keyUser = datastore.save(user);
        LOG.log(Level.INFO, "SAVED ID: {0}", keyUser.getId());

        return user;

    }

    public List<User> findAll() {
        return datastore.find(User.class).asList();
    }

    public List<User> findBy(String field, Object value) {
        return datastore.createQuery(User.class).field(field).equal(340840342).asList();
        //or
        //return datastore.find(User.class, field", value).asList()
    }

    public User findById(Object id) {
        return datastore.get(User.class, id);
    }

    public User update(String id, String field, Object value) {
        Query<User> findQuery = datastore.createQuery(User.class).field(Mapper.ID_KEY).equal(id);
        UpdateOperations<User> updateOps = datastore.createUpdateOperations(User.class).set(field, value);
        datastore.update(findQuery, updateOps);

        return datastore.get(User.class, id);

    }

    public void delete(Object id) {
        Query<User> findQuery = datastore.createQuery(User.class).field(Mapper.ID_KEY).equal(id);
        datastore.delete(findQuery);
    }

}
