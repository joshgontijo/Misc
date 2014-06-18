//package com.josue.jee.mongo.basic.storage;
//
//import com.josue.jee.mongo.basic.bean.Address;
//import com.josue.jee.mongo.basic.bean.User;
//import java.io.File;
//import java.util.Random;
//import java.util.UUID;
//import junit.framework.Assert;
//import org.jboss.arquillian.container.test.api.Deployment;
//import org.jboss.arquillian.container.test.api.TargetsContainer;
//import org.jboss.arquillian.junit.Arquillian;
//import org.jboss.shrinkwrap.api.ShrinkWrap;
//import org.jboss.shrinkwrap.api.asset.EmptyAsset;
//import org.jboss.shrinkwrap.api.spec.WebArchive;
//import static org.jboss.shrinkwrap.resolver.api.maven.Maven.resolver;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
///**
// *
// * @author Josue
// */
//@RunWith(Arquillian.class)
//public class MorphiaTest {
//
////    @Inject
//    MorphiaStorage storage;
//
//    @Deployment
//    @TargetsContainer("wildfly-managed")
//    public static WebArchive createDeployment() {
//
//        File[] dependecies = resolver()
//                .loadPomFromFile("pom.xml")
//                .resolve("org.mongodb.morphia:morphia:0.107",
//                        "org.mongodb:mongo-java-driver:2.12.2")
//                .withTransitivity().asFile();
//
//        WebArchive war = ShrinkWrap
//                .create(WebArchive.class, "wildfly-test.jar")
//                .addPackage(User.class.getPackage())
//                .addPackage(MorphiaStorage.class.getPackage())
//                .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml")
//                .addAsLibraries(dependecies);
//
//        return war;
//    }
//
//    @Test
//    public void testSave() {
//        User resultUser = storage.createUser(createUser("josue"));
//        User foundUser = storage.findById(resultUser.getUuid());
//
//        Assert.assertNotNull(foundUser);
//        Assert.assertEquals(resultUser, foundUser);
//    }
//
//    @Test
//    public void testFindBy() {
//
//    }
//
//    private User createUser(String name) {
//        User user = new User();
//        user.setUuid(UUID.randomUUID().toString());
//        user.setIdade(new Random().nextInt(Integer.MAX_VALUE) + 1);
//        user.setName(name);
//
//        Address address = new Address();
//        address.setCity("CITY-" + UUID.randomUUID().toString());
//        address.setNumber(new Random().nextInt(Integer.MAX_VALUE) + 1);
//        address.setStreet("STREET-" + UUID.randomUUID().toString());
//
//        user.setAddress(address);
//
//        return user;
//
//    }
//}
