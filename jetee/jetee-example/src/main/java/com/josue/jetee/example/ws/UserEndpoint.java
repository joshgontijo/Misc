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
@ServerEndpoint(
        value = "/events",
        encoders = UserEncoder.class,
        decoders = UserEncoder.class)
public class UserEndpoint {

    @Inject
    private EntityManager em;

    private Session session;

    @OnOpen
    public void onWebSocketConnect(Session sess) {
        System.out.println("Socket Connected: " + sess);
        this.session = sess;
        User user = em.find(User.class, "234");
    }

    @OnMessage
    public void onMessage(User user) {
        System.out.println("Received USER message: " + user.toString() + " echoing back");
        session.getAsyncRemote().sendObject(user);
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