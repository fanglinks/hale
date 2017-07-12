package org.hale.lens.server;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.hk2.utilities.ServiceLocatorUtilities;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;


/**
 * Created by guilherme on 7/11/17.
 * hale
 */
public class ApiServer {

    private static final Logger logger = LoggerFactory.getLogger(ApiServer.class);

    public ApiServer() {
    }


//    // Base URI the Grizzly HTTP server will listen on
//    public static final String BASE_URI = "http://localhost:8080/myapp/";
//
//    /**
//     * Starts Grizzly HTTP server exposing JAX-RS resources defined in this application.
//     *
//     * @return Grizzly HTTP server.
//     */
//    public static HttpServer startServer() {
//        // create a resource config that scans for JAX-RS resources and providers
//        // in com.example package
//        final ResourceConfig rc = new ResourceConfig().packages("org.hale.lens");
//
//        // create and start a new instance of grizzly http server
//        // exposing the Jersey application at BASE_URI
//        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
//    }
//
//    /**
//     * Main method.
//     *
//     * @param args
//     * @throws IOException
//     */
//    public static void main(String[] args) throws IOException {
//        final HttpServer server = startServer();
//        System.out.println(String.format("Jersey app started with WADL available at "
//                + "%sapplication.wadl\nHit enter to stop it...", BASE_URI));
//        System.in.read();
//        server.stop();
//    }

    public static void main(String[] args) {

        String BASE_URI = "http://0.0.0.0:9005/api";
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), new JerseyApplication(), locator);

        try {
            httpServer.start();

            System.out.println(String.format("Jersey app started.\nHit enter to stop it...", BASE_URI));
            System.in.read();
        } catch (IOException e) {
            logger.error("error starting server: " + e.getLocalizedMessage(), e);
        }
    }
}
