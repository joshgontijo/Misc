package com.josue.jetee.example.ws;

import com.josue.jetee.example.user.User;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ClientEndpoint
@ServerEndpoint(value = "/events")
public class HelloEndpoint {

    @Inject
    private EntityManager em;

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        System.out.println("Socket Connected: " + sess);
        User user = em.find(User.class, "234");
    }

    @OnMessage
    public void onWebSocketText(String message) {
        System.out.println("Received TEXT message: " + message);
    }

    @OnClose
    public void onWebSocketClose(CloseReason reason) {
        System.out.println("Socket Closed: " + reason);
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        cause.printStackTrace(System.err);
    }
}