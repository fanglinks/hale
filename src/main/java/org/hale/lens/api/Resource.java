package org.hale.lens.api;

import org.hale.commons.io.neo4j.Neo4jSessionFactory;
import org.hale.weaver.repository.domain.EntityRepository;
import org.neo4j.ogm.session.Session;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.time.LocalDateTime;


/**
 * Created by guilherme on 7/11/17.
 * hale
 */
@Path("resource")
public class Resource {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String get() {
        return String.format("Hello %s", LocalDateTime.now().toString());
    }
}
