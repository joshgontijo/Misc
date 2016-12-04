package com.josue.embedded.jetty;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by Josue on 30/11/2016.
 */
public class Main {

    private static final String WEBAPP_RESOURCES_LOCATION = "webapp";


    public static void main(String[] args) throws Exception {
        Server server = new Server(8080);

        WebAppContext context = new WebAppContext();

        setupWeb(context);
        setupServlets(context);
        context.addServlet(getJAXRSServlet(), "/api/*");
        setupCDI(context);

        server.setHandler(context);
        server.start();
        server.join();
    }

    private static ServletHolder getJAXRSServlet() {
        ResourceConfig config = new ResourceConfig();
        config.packages("com.josue");
        return new ServletHolder(new ServletContainer(config));
    }

    private static void setupServlets(ServletContextHandler context){
        context.addServlet(HelloServlet.class, "/hello");
    }

    private static void setupWeb(WebAppContext context) throws URISyntaxException {
        context.setContextPath("/");
        context.setDescriptor(WEBAPP_RESOURCES_LOCATION + "/WEB-INF/web.xml");

        URL webAppDir = Thread.currentThread().getContextClassLoader().getResource(WEBAPP_RESOURCES_LOCATION);
        if (webAppDir == null) {
            throw new RuntimeException(String.format("No %s directory was found into the JAR file", WEBAPP_RESOURCES_LOCATION));
        }
        context.setResourceBase(webAppDir.toURI().toString());
        context.setParentLoaderPriority(true);
    }

    private static void setupCDI(ServletContextHandler context){
        //CDI
        context.addEventListener(new Listener());
    }

    public static class HelloServlet extends HttpServlet {

        @Inject
        private UserControl control;

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>New Hello Simple Servlet: " + control.getUser() + "</h1>");
        }
    }
}
