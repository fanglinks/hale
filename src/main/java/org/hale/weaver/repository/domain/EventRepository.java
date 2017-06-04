package org.hale.weaver.repository.domain;

import org.hale.commons.types.Constants;
import org.hale.commons.types.domain.Entity;
import org.hale.commons.types.domain.Event;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.session.Session;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created on 2017-04-29
 * hale
 * <p>
 * Event repository
 *
 * @author guilherme
 */
public class EventRepository {

    private Session session;

    public EventRepository(Session session){
        this.setSession(session);
    }

    protected void setSession(Session session) {
        this.session = session;
    }

    public Session getSession() {
        return session;
    }

    /**
     * Searches for actions
     *
     * @param e
     * @return
     */
    public Event find(Event e) {
        String queryTemplate =
                String.format("MATCH (agent :%s {type: {srcType}, id: {srcId}})-[:%s]->" +
                                "(event :%s)-[:%s]->(element :%s {type: {destType}, id: {destId}})" +
                                "\nRETURN event",
                        Constants.ENTITY, Constants.REL_START, Constants.EVENT, Constants.REL_END, Constants.ENTITY);

        Map<String, Object> params = new HashMap<>();
        params.put("srcType", e.getAgent().getType());
        params.put("srcId", e.getAgent().getId());
        params.put("type", e.getType());
        params.put("destType", e.getElement().getType());
        params.put("destId", e.getElement().getId());

        Iterator<Event> iterator = session.query(getEntityType(), queryTemplate, params).iterator();

        return iterator.hasNext() ? iterator.next() : null;
    }

    /**
     * Searches for actions of a given type, tied to an agent
     *
     * @param agent
     * @param type
     * @return
     */
    public Iterable<Event> findAllByAgent(Entity agent, String type) {
        String queryTemplate =
                String.format("MATCH (agent :%s {type: {srcType}, id: {srcId}})-[:%s]->(event :%s)-[:%s]->" +
                                "()" +
                                "\nRETURN event",
                        Constants.ENTITY, Constants.REL_START, Constants.EVENT, Constants.REL_END);

        Map<String, Object> params = new HashMap<>();
        params.put("srcType", agent.getType());
        params.put("srcId", agent.getId());
        params.put("type", type);

        return session.query(getEntityType(), queryTemplate, params);
    }

    /**
     * Searches for actions of a given type, tied to an element
     *
     * @param element
     * @param type
     * @return
     */
    public Iterable<Event> findAllByElement(Entity element, String type) {
        String queryTemplate =
                String.format("MATCH ()-[:%s]->(event :%s)-[:%s]->" +
                                "(element :%s {type: {destType}, id: {destId}})" +
                                "\nRETURN event",
                        Constants.REL_START, Constants.EVENT, Constants.REL_END, Constants.ENTITY);

        Map<String, Object> params = new HashMap<>();
        params.put("destType", element.getType());
        params.put("destId", element.getId());
        params.put("type", type);

        return session.query(getEntityType(), queryTemplate, params);
    }


    /**
     * Searches for all actions of a given type
     *
     * @param type entity type
     * @return all entities in database of type provided
     */
    public Iterable<Event> findAllByType(String type) {
        return session.loadAll(getEntityType(),
                new Filter(Constants.FIELD_TYPE, ComparisonOperator.EQUALS, type));
    }

    /**
     * Checks if an event exists
     *
     * @param e
     * @return
     */
    public boolean exists(Event e){
        return find(e) != null;
    }

    /**
     * Deletes the given entity, if it exists. Otherwise, nothing happens.
     *
     * @param e Instance of entity to save
     */
    public void delete(Event e) {
        session.delete(e);
    }

    /**
     * Saves the entity in the data store.
     *
     * @param e Instance of entity to save
     */
    public void save(Event e) {
        session.save(e);
    }

    /**
     * Saves a list of given entities
     *
     * @param entities List of entities to save
     */
    public void save(Iterable<Event> entities) {
        entities.forEach(e -> session.save(e));
    }

    protected Class<Event> getEntityType() {
        return Event.class;
    }
}
