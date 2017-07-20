package org.hale.weaver.repository.domain;

import org.hale.commons.Page;
import org.hale.commons.types.Constants;
import org.hale.commons.types.basic.Entity;
import org.neo4j.ogm.cypher.BooleanOperator;
import org.neo4j.ogm.cypher.ComparisonOperator;
import org.neo4j.ogm.cypher.Filter;
import org.neo4j.ogm.cypher.Filters;
import org.neo4j.ogm.cypher.query.Pagination;
import org.neo4j.ogm.session.Session;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created on 2017-03-30
 * hale
 *
 * @author guilherme
 */
public class EntityRepository {

    public EntityRepository(Session session) {
        this.setSession(session);
    }

    private Session session;

    protected void setSession(Session session){
        this.session = session;
    }

    public Session getSession(){
        return session;
    }

    /**
     *
     * @param e Type entity
     * @return
     */
    public Entity get(Entity e){
        Filter filterType = new Filter(Constants.FIELD_TYPE, ComparisonOperator.EQUALS, e.getType());
        Filter filterId = new Filter(Constants.FIELD_ID, ComparisonOperator.EQUALS, e.getId());
        filterId.setBooleanOperator(BooleanOperator.AND);

        Filters filters = new Filters(Arrays.asList(filterType, filterId));

        Collection<Entity> entities = session.loadAll(getEntityType(), filters);

        return entities.iterator().next();
    }

    /**
     *
     * @param e
     * @return
     */
    public Entity find(Entity e){
        Filter filterType = new Filter(Constants.FIELD_TYPE, ComparisonOperator.EQUALS, e.getType());
        Filter filterId = new Filter(Constants.FIELD_ID, ComparisonOperator.EQUALS, e.getId());
        filterId.setBooleanOperator(BooleanOperator.AND);

        Filters filters = new Filters(Arrays.asList(filterType, filterId));

        Collection<Entity> entities = session.loadAll(getEntityType(), filters);

        Iterator<Entity> iterator = entities.iterator();

        return iterator.hasNext() ? iterator.next() : null;
    }

    /**
     *
     * @param Page
     * @return
     */
    public Iterable<Entity> findAll(Page Page){

        Pagination pagination = new Pagination(Page.getNumber(), Page.getSize());

        return session.loadAll(getEntityType(),
                pagination);
    }

    /**
     *
     * @param type entity type
     * @return all entities in database of type provided
     */
    public Iterable<Entity> findAll(String type, Page Page){

        Pagination pagination = new Pagination(Page.getNumber(), Page.getSize());

        return session.loadAll(getEntityType(),
                new Filter(Constants.FIELD_TYPE, ComparisonOperator.EQUALS, type),
                pagination);
    }

    /**
     *
     * @param e Type entity
     * @return
     */
    public boolean exists(Entity e){
        return find(e) != null;
    }

    /**
     * Deletes the given entity, if it exists. Otherwise, nothing happens.
     *
     * @param e Instance of entity to save
     *
     */
    public void delete(Entity e){
        session.delete(e);
    }

    /**
     * Saves the entity in the data store.
     *
     * @param e Instance of entity to save
     */
    public void save(Entity e){
        session.save(e);
    }

    /**
     * Saves a list of given entities
     *
     * @param entities List of entities to save
     *
     */
    public void save(Iterable<Entity> entities){
        entities.forEach(e -> session.save(e));
    }

    protected Class<Entity> getEntityType() {
        return Entity.class;
    }
}
