package com.josue.jetee.example;


import com.josue.jetee.JettyServer;
import com.josue.jetee.example.ws.HelloEndpoint;

/**
 * Created by Josue on 01/01/2017.
 */
public class Main {

    private JettyServer server;

    public static void main(String[] args) throws Exception {
        new JettyServer(8080)
                .addServlet(HelloServlet.class, "/hello")
                .enableJCache()
                .enableJAXRS("/api/*", "com.josue.jetee.example")
                .enableWebsocket("/ws")
                .addWSEndpoint(HelloEndpoint.class)
                .start();
    }

}
