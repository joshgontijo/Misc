package com.josue.embedded.jetty;

import com.josue.embedded.jetty.user.User;
import com.josue.embedded.jetty.user.UserJpaRepository;
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
import java.util.Arrays;
import java.util.List;

/**
 * Created by Josue on 30/11/2016.
 */
public class JettyServer {

    private static final String WEBAPP_RESOURCES_LOCATION = "webapp";

    //TODO move
    private final Server server = new Server(8080);

    public static void main(String[] args) throws Exception {
        new JettyServer().start();
    }

    public void start() throws Exception {
        WebAppContext context = new WebAppContext();
        server.setHandler(context);

        setupWeb(context);
        setupServlets(context);
        context.addServlet(getJAXRSServlet(), "/api/*");
        setupCDI(context);
        setupJpa(context);

        server.start();
//        server.join();
    }

    public void shutdown() throws Exception {
        server.stop();
    }

    private void setupJpa(ServletContextHandler context) {
        context.addEventListener(new JPAServletListener());
    }


    private ServletHolder getJAXRSServlet() {
        ResourceConfig config = new ResourceConfig();
        config.packages("com.josue");
        return new ServletHolder(new ServletContainer(config));
    }

    private void setupServlets(ServletContextHandler context) {
        context.addServlet(HelloServlet.class, "/hello");
    }

    private void setupWeb(WebAppContext context) throws URISyntaxException {
        context.setContextPath("/");
        context.setDescriptor(WEBAPP_RESOURCES_LOCATION + "/WEB-INF/web.xml");

        URL webAppDir = Thread.currentThread().getContextClassLoader().getResource(WEBAPP_RESOURCES_LOCATION);
        if (webAppDir == null) {
            throw new RuntimeException(String.format("No %s directory was found into the JAR file", WEBAPP_RESOURCES_LOCATION));
        }
        context.setResourceBase(webAppDir.toURI().toString());
        context.setParentLoaderPriority(true);
    }

    private void setupCDI(ServletContextHandler context) {
        //CDI
        context.addEventListener(new Listener());
    }

    public static class HelloServlet extends HttpServlet {

        @Inject
        private UserJpaRepository repository;

        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

            List<User> found = repository.getAll();
            String users = Arrays.toString(found.toArray(new User[found.size()]));

            response.setContentType("text/html");
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println("<h1>New Hello Simple Servlet: " + users + "</h1>");
        }
    }
}
