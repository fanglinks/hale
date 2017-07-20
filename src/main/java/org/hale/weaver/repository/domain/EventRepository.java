package org.hale.weaver.repository.domain;

import org.hale.commons.Page;
import org.hale.commons.types.Constants;
import org.hale.commons.types.basic.Event;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.session.Session;

import java.util.*;

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
     * Retrieves specified event instance from database
     *
     * @param e Event object
     * @return
     */
    public Event get(Event e) {

        Filter filterType = new Filter(Constants.FIELD_TYPE, ComparisonOperator.EQUALS, e.getType());
        Filter filterId = new Filter(Constants.FIELD_ID, ComparisonOperator.EQUALS, e.getId());
        filterId.setBooleanOperator(BooleanOperator.AND);

        Filters filters = new Filters(Arrays.asList(filterType, filterId));

        Collection<Event> events = session.loadAll(getEntityType(), filters, 1);

        Iterator<Event> iterator = events.iterator();

        return iterator.next();
    }

    /**
     * Searches for given Event instance in database
     *
     * @param e
     * @return
     */
    public Event find(Event e) {
        Filter filterType = new Filter(Constants.FIELD_TYPE, ComparisonOperator.EQUALS, e.getType());
        Filter filterId = new Filter(Constants.FIELD_ID, ComparisonOperator.EQUALS, e.getId());
        filterId.setBooleanOperator(BooleanOperator.AND);

        Filters filters = new Filters(Arrays.asList(filterType, filterId));

        Collection<Event> events = session.loadAll(getEntityType(), filters, 1);

        Iterator<Event> iterator = events.iterator();

        return iterator.hasNext() ? iterator.next() : null;
    }

    /**
     * Searches for an Event in the database based on Id
     *
     * @param id
     * @return
     */
    public Event findById(String id) {
        Filter filterId = new Filter(Constants.FIELD_ID, ComparisonOperator.EQUALS, id);
        filterId.setBooleanOperator(BooleanOperator.AND);

        Filters filters = new Filters(Arrays.asList(filterId));

        Collection<Event> events = session.loadAll(getEntityType(), filters, 1);

        Iterator<Event> iterator = events.iterator();

        return iterator.hasNext() ? iterator.next() : null;
    }

    public Iterable<Event> findAll(Page Page){

        Pagination pagination = new Pagination(Page.getNumber(), Page.getSize());

        return session.loadAll(getEntityType(),
                pagination, 1);
    }

    public Iterable<Event> findAll(String type, Page Page){

        Pagination pagination = new Pagination(Page.getNumber(), Page.getSize());

        return session.loadAll(getEntityType(),
                new Filter(Constants.FIELD_TYPE, ComparisonOperator.EQUALS, type),
                pagination, 1);
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
