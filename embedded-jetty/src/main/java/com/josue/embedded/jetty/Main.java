package com.josue.embedded.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Josue on 30/11/2016.
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        ServletContextHandler context = new ServletContextHandler(server, "/*");
        context.addServlet(getJAXRSServlet(), "/api/*");
        context.addServlet(HelloServlet.class, "/hello-servlet");

        context.addEventListener(new Listener());

        server.start();
        server.join();
    }

    private static ServletHolder getJAXRSServlet() {
        ResourceConfig config = new ResourceConfig();
        config.packages("com.josue");
        return new ServletHolder(new ServletContainer(config));
    }

    public static class HelloServlet extends HttpServlet {
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>New Hello Simple Servlet</h1>");
        }
    }
}
