package org.hale.commons.types.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hale.commons.types.Constants;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.UUID;

/**
 * Created by guilherme on 2017-03-20.
 */
@NodeEntity(label = Constants.EVENT)
public class Event {

    @GraphId
    @JsonIgnore
    private Long graphId;

    @Index
    private String id;

    @Index
    private String type;

    @Relationship(type = Constants.REL_START, direction = Relationship.INCOMING)
    private Entity agent;

    @Relationship(type = Constants.REL_END, direction = Relationship.OUTGOING)
    private Entity element;

    private Long timestamp;

    private Double weight;

    private String context;

    public Event() {
        super();
        this.setId(UUID.randomUUID().toString());
    }

    public Event(Entity agent, String type, Entity element) {
        this(agent, type, element, null, null, null);
    }

    public Event(Entity agent, String type, Entity element, Long timestamp, Double weight, String context) {
        this();
        this.agent = agent;
        this.element = element;
        this.timestamp = timestamp;
        this.weight = weight;
        this.context = context;
        this.type = type;
    }

    public Event(String id, Entity agent, String type, Entity element, Long timestamp, Double weight, String context) {
        this.id = id;
        this.agent = agent;
        this.element = element;
        this.timestamp = timestamp;
        this.weight = weight;
        this.context = context;
        this.type = type;
    }

    public Long getGraphId() {
        return graphId;
    }

    public void setGraphId(Long graphId) {
        this.graphId = graphId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Entity getAgent() {
        return agent;
    }

    public void setAgent(Entity agent) {
        this.agent = agent;
    }

    public Entity getElement() {
        return element;
    }

    public void setElement(Entity element) {
        this.element = element;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event)) return false;

        Event type1 = (Event) o;

        if (!id.equals(type1.id)) return false;
        return type.equals(type1.type);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + type.hashCode();
        return result;
    }
}
