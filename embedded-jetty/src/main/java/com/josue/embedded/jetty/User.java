package com.josue.embedded.jetty;

import java.util.Date;

/**
 * Created by Josue on 30/11/2016.
 */
public class User {

    private final String name;
    private final int age;
    private final Date date;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
        this.date = new Date();

    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public Date getDate() {
        return date;
    }
}
