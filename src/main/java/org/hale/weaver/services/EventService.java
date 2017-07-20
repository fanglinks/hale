package org.hale.weaver.services;

import org.hale.commons.Page;
import org.hale.commons.types.Constants;
import org.hale.commons.types.basic.Event;
import org.hale.weaver.repository.domain.EventRepository;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;

import java.util.*;

/**
 * Created by guilherme on 7/20/17.
 * hale
 */
public class EventService {

    private Session session;

    public EventService(Session session){
        this.setSession(session);
    }

    protected void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    public Iterable<Event> search(String type, String agentType, String agentId,
                                  String elementType, String elementId, Page page){

        StringJoiner stringJoiner = new StringJoiner("");
        Map<String, Object> params = new HashMap<>();

        if (agentType != null){
            if (agentId != null){
                stringJoiner.add(
                        String.format("MATCH (agent :%s {type: {agentType}, id: {agentId}})-[:%s]->",
                                Constants.ENTITY, Constants.REL_START));
                params.put("agentType", agentType);
                params.put("agentId", agentId);
            } else {
                stringJoiner.add(
                        String.format("MATCH (agent :%s {type: {agentType}})-[:%s]->",
                                Constants.ENTITY, Constants.REL_START));
                params.put("agentType", agentType);
            }
        } else {
            stringJoiner.add(
                    String.format("MATCH (agent :%s)-[:%s]->",
                            Constants.ENTITY, Constants.REL_START));
        }

        if (type != null) {
            stringJoiner.add(
                    String.format("(event :%s {type: {eventType}})",
                            Constants.EVENT));
            params.put("eventType", type);
        } else {
            stringJoiner.add(
                    String.format("(event :%s)",
                            Constants.EVENT));
        }

        if (elementType != null) {
            if (elementId != null) {
                stringJoiner.add(
                        String.format(
                                "-[:%s]->(element :%s {type: {elementType}, id: {elementId}})",
                                Constants.REL_END, Constants.ENTITY));
                params.put("elementType", elementType);
                params.put("elementId", elementId);
            } else {
                stringJoiner.add(
                        String.format(
                                "-[:%s]->(element :%s {type: {elementType}})",
                                Constants.REL_END, Constants.ENTITY));
                params.put("elementType", elementType);
            }
        } else {
            stringJoiner.add(
                    String.format(
                            "-[:%s]->(element :%s)",
                            Constants.REL_END, Constants.ENTITY));
        }

        stringJoiner.add("\nRETURN event.id AS id");
        stringJoiner.add("\nSKIP {skip}");
        stringJoiner.add("\nLIMIT {limit}");

        params.put("skip", page.getNumber()*page.getSize());
        params.put("limit", page.getSize());

        String queryTemplate = stringJoiner.toString();
        Iterable<String> eventsId = session.query(String.class, queryTemplate, params);

        List<Event> events = new LinkedList<>();

        EventRepository eventRepository = new EventRepository(session);

        for(String id: eventsId){
            events.add(eventRepository.findById(id));
        }

        return events;
    }
}
