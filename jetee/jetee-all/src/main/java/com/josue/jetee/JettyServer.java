package com.josue.jetee;

import com.josue.jetee.jcache.CacheListener;
import com.josue.jetee.jpa.JPAServletListener;
import org.eclipse.jetty.cdi.websocket.WebSocketCdiListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.ContextHandlerCollection;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.webapp.WebAppContext;
import org.eclipse.jetty.websocket.jsr356.server.deploy.WebSocketServerContainerInitializer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.servlet.ServletContainer;
import org.jboss.weld.environment.servlet.Listener;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.websocket.DeploymentException;
import javax.websocket.server.ServerContainer;
import javax.websocket.server.ServerEndpointConfig;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Logger;

/**
 * Created by Josue on 30/11/2016.
 */
public class JettyServer {

    private static final String WEBAPP_RESOURCES_LOCATION = "webapp";
    public static final String PERSISTENCE_XML_LOCATION = "META-INF/persistence.xml";
    private static final String BEANS_XML_LOCATION = "META-INF/beans.xml";
    private static final String WEB_XML_LOCATION = WEBAPP_RESOURCES_LOCATION + "/WEB-INF/web.xml";

    private static final Logger logger = Logger.getLogger(JPAServletListener.class.getName());

    private final Server server;
    private final WebAppContext context;
    //ws
    private ServletContextHandler wsContext;
    private ServerContainer wscontainer;

    private final Listener listener = new Listener();

    public JettyServer(int port) {
        this.server = new Server(port);

        this.context = new WebAppContext();
        context.setContextPath("/");
        context.setParentLoaderPriority(true);

    }

    public JettyServer addServlet(Class<? extends Servlet> servletType, String path) {
        context.addServlet(servletType, path);
        return this;
    }

    public JettyServer enableJCache() {
        context.addEventListener(new CacheListener());
        return this;
    }

    public JettyServer enableJAXRS(String path, String scanPackage) {
        ResourceConfig config = new ResourceConfig();
        config.packages(scanPackage);
        ServletHolder servletHolder = new ServletHolder(new ServletContainer(config));
        context.addServlet(servletHolder, path);
        return this;
    }

    public JettyServer enableWebsocket(String rootPath) throws ServletException {
        wsContext = new ServletContextHandler(ServletContextHandler.SESSIONS);
        wsContext.setContextPath(rootPath);
        wsContext.setServer(server);

        wsContext.addEventListener(listener);


        wsContext.addLifeCycleListener(new WebSocketCdiListener());
        wscontainer = WebSocketServerContainerInitializer.configureContext(wsContext);
//        WebSocketCdiInitializer.configureContext(wsContext);

        return this;
    }

    public JettyServer addWSEndpoint(Class<?> endpoint) throws DeploymentException {
        if (wsContext == null || wscontainer == null) {
            throw new IllegalStateException("Websocket not enabled");
        }
        wscontainer.addEndpoint(endpoint);
        return this;
    }

    public JettyServer addWSEndpoint(ServerEndpointConfig endpointConfig) throws DeploymentException {
        if (wsContext == null || wscontainer == null) {
            throw new IllegalStateException("Websocket not enabled");
        }
        wscontainer.addEndpoint(endpointConfig);
        return this;
    }

    public void start() throws Exception {
        setupWeb();
        setupCDI();
        setupJpa();

        //provide 2 different contexts, one for webapp another for WS
        ContextHandlerCollection contexts = new ContextHandlerCollection();
        contexts.addHandler(context);
        if (wsContext != null) {
            contexts.addHandler(wsContext);
        }

        server.setHandler(contexts);
//        server.dump(System.err);
        server.start();
    }


    public void shutdown() throws Exception {
        server.stop();
    }

    private void setupCDI() {
        URL persistence = Thread.currentThread().getContextClassLoader().getResource(PERSISTENCE_XML_LOCATION);
        if (persistence == null) {
            logger.warning("### '" + BEANS_XML_LOCATION + "' not found ###");
        }
        context.addEventListener(listener);
    }

    private void setupJpa() {
        URL persistence = Thread.currentThread().getContextClassLoader().getResource(PERSISTENCE_XML_LOCATION);
        if (persistence == null) {
            logger.warning("### '" + PERSISTENCE_XML_LOCATION + "' not found ###");
        } else {
            logger.info("### '" + PERSISTENCE_XML_LOCATION + "' found... bootstrapping JPA ###");
            context.addEventListener(new JPAServletListener());
        }

    }


    private void setupWeb() throws URISyntaxException {
        URL webAppDir = Thread.currentThread().getContextClassLoader().getResource(WEBAPP_RESOURCES_LOCATION);
        if (webAppDir == null) {
            logger.warning("### '" + WEBAPP_RESOURCES_LOCATION + "' not found ###");
            throw new RuntimeException(String.format("No %s directory was found", WEBAPP_RESOURCES_LOCATION));
        } else {
            logger.info("### '" + WEBAPP_RESOURCES_LOCATION + "' found, bootstrapping web pages support ###");
            context.setResourceBase(webAppDir.toURI().toString());

        }

        URL webXml = Thread.currentThread().getContextClassLoader().getResource(WEB_XML_LOCATION);
        if (webXml == null) {
            logger.warning("### '" + WEB_XML_LOCATION + "' not found ###");
        } else {
            logger.info("### '" + WEB_XML_LOCATION + "' found ###");
            context.setDescriptor(WEB_XML_LOCATION);
        }
    }

}
