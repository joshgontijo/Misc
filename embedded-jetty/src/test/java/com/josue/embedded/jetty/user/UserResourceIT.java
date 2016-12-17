package com.josue.embedded.jetty.user;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.josue.embedded.jetty.CustomJacksonProvider;
import com.josue.embedded.jetty.JettyServer;
import io.restassured.RestAssured;
import io.restassured.config.RestAssuredConfig;
import io.restassured.http.ContentType;
import io.restassured.mapper.factory.DefaultJackson2ObjectMapperFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static io.restassured.config.ObjectMapperConfig.objectMapperConfig;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;


public class UserResourceIT {

    private static JettyServer server = new JettyServer();

    @BeforeClass
    public static void init() throws Exception {
        RestAssured.baseURI = "http://localhost:8080";
        RestAssured.basePath = "api";
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory(
                new DefaultJackson2ObjectMapperFactory() {
                    @Override
                    public ObjectMapper create(Class cls, String charset) {
                        //custom jackson mapper for date
                        return new CustomJacksonProvider().getContext(null);
                    }
                }
        ));

        server.start();
    }

    @AfterClass
    public static void shutdown() throws Exception {
        server.shutdown();
    }

    @Test
    public void testGetAll() throws Exception {
        User user = new User();
        user.setName("Josh");
        user.setAge(27);

        User created = create(user);
        when().get("/users").then().assertThat().body("$", hasSize(greaterThan(1)));
    }

    @Test
    public void testGetById() throws Exception {
        User user = new User();
        user.setName("Josh");
        user.setAge(27);

        User created = create(user);
        when().get("/users/" + created.getId()).then()
                .assertThat().body("$", not(nullValue())).and()
                .body("id", equalTo(created.getId()));
    }

    @Test
    public void testCreate() throws Exception {
        User user = new User();
        user.setName("Josh");
        user.setAge(27);

        create(user);
    }

    private User create(User user) {
        return given().body(user).contentType(ContentType.JSON)
                .post("/users").then()
                .assertThat().statusCode(201)
                .and().body("id", not(nullValue())).and()
                .extract().body().as(User.class);

    }

}