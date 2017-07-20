package org.hale.lens.api;

import org.hale.commons.Page;
import org.hale.commons.io.collection.ListSink;
import org.hale.commons.io.neo4j.Neo4jSessionFactory;
import org.hale.commons.types.basic.Event;
import org.hale.weaver.repository.domain.EventRepository;
import org.hale.weaver.services.EventService;
import org.hale.weaver.services.QueryRunner;
import org.hale.weaver.services.meta.ListEventTypeQuery;
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
 * Created by guilherme on 7/14/17.
 * hale
 */
@Path("/events")
public class EventResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@DefaultValue("0") @QueryParam("page") int pageNumber,
                         @DefaultValue("10") @QueryParam("pageSize") int pageSize) {

        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        EventRepository eventRepository = new EventRepository(session);

        Page page = new Page(pageNumber, pageSize);
        Iterable<Event> events = eventRepository.findAll(page);

        return Response.ok(events).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") String id) {

        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        EventRepository eventRepository = new EventRepository(session);

        Event event = eventRepository.findById(id);

        if (event != null){
            return Response.ok(event).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") String id){
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        EventRepository eventRepository = new EventRepository(session);

        Event event = eventRepository.findById(id);

        if (event != null){
            eventRepository.delete(event);
            return Response.ok().build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @GET
    @Path("/search")
    @Produces(MediaType.APPLICATION_JSON)
    public Response search(@QueryParam("type") String type,
            @QueryParam("agentType") String agentType, @QueryParam("agentId") String agentId,
            @QueryParam("elementType") String elementType, @QueryParam("elementId") String elementId,
            @DefaultValue("0") @QueryParam("page") int pageNumber,
            @DefaultValue("10") @QueryParam("pageSize") int pageSize) {

        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        EventService eventService = new EventService(session);

        Page page = new Page(pageNumber, pageSize);
        Iterable<Event> events = eventService.search(type, agentType, agentId, elementType, elementId, page);

        return Response.ok(events).build();
    }

    @GET
    @Path("/metadata")
    @Produces(MediaType.APPLICATION_JSON)
    public Response metadata(){
        Session session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        QueryRunner queryRunner = new QueryRunner(session);
        ListEventTypeQuery listEventTypeQuery = new ListEventTypeQuery();
        List<String> eventTypes = new LinkedList<>();

        queryRunner.run(listEventTypeQuery, new ListSink<>(eventTypes));

        LocalDateTime dateTime = LocalDateTime.now();

        Map<String, Object> response = new HashMap<>();
        response.put("types", eventTypes);
        response.put("date", dateTime.toLocalDate().toString());
        response.put("time", dateTime.toLocalTime().toString());

        return Response.ok(response).build();
    }
}
