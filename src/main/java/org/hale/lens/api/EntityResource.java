package org.hale.lens.api;

import org.hale.commons.io.collection.ListSink;
import org.hale.commons.io.neo4j.Neo4jSessionFactory;
import org.hale.commons.types.domain.Entity;
import org.hale.lens.io.EntityDTO;
import org.hale.weaver.repository.domain.EntityRepository;
import org.hale.weaver.services.QueryRunner;
import org.hale.weaver.services.meta.TypeLabelsQuery;
import org.json.JSONObject;
import org.neo4j.ogm.session.Session;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Created by guilherme on 7/11/17.
 * hale
 */
@Path("/entities")
public class EntityResource {

    @GET
    @Path("/{type}/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") String id,
                       @PathParam("type") String type) {

        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        EntityRepository entityRepository = new EntityRepository(session);

        Entity e = entityRepository.find(new Entity(id, type));

        if (e != null){
            return Response.ok(e).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(EntityDTO entityDTO) {

        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        EntityRepository entityRepository = new EntityRepository(session);

        Entity e = new Entity(entityDTO.getId(), entityDTO.getType());
        entityRepository.save(e);

        return Response.ok(entityRepository.find(e)).build();
    }


    @DELETE
    @Path("/{type}/{id}")
    public Response delete(@PathParam("id") String id,
                       @PathParam("type") String type) {

        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        EntityRepository entityRepository = new EntityRepository(session);
        Entity e = entityRepository.find(new Entity(id, type));
        if (e != null) entityRepository.delete(e);

        return Response.ok().build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response metadata(){
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        QueryRunner queryRunner = new QueryRunner(session);
        TypeLabelsQuery typeLabelsQuery = new TypeLabelsQuery();
        List<String> labels = new LinkedList<>();

        queryRunner.run(typeLabelsQuery, new ListSink<>(labels));

        LocalDateTime dateTime = LocalDateTime.now();


        Map<String, Object> metadata = new HashMap<>();
        metadata.put("types", labels);
        Map<String, Object> response = new HashMap<>();
        response.put("metadata", metadata);
        response.put("date", dateTime.toLocalDate().toString());
        response.put("time", dateTime.toLocalTime().toString());

        return Response.ok(response).build();

    }
}
