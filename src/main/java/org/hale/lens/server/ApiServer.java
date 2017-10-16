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

    public static void main(String[] args) {

        String BASE_URI = "http://0.0.0.0:9005/api";
        ServiceLocator locator = ServiceLocatorUtilities.createAndPopulateServiceLocator();

        HttpServer httpServer = GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), new JerseyApplication(), locator);

        try {
            httpServer.start();

            System.out.println(String.format("Jersey app started at %s.\nHit enter to stop it...", BASE_URI));
            System.in.read();
        } catch (IOException e) {
            logger.error("error starting server: " + e.getLocalizedMessage(), e);
        }
    }
}
