package org.hale.lens.io;

/**
 * Created by guilherme on 7/13/17.
 * hale
 */
public class EventDTO {

    private EntityDTO agent;
    private EntityDTO element;
    private String type;
    private String id;
    private Long timestamp;
    private String context;
    private Double weight;

    public EntityDTO getAgent() {
        return agent;
    }

    public void setAgent(EntityDTO agent) {
        this.agent = agent;
    }

    public EntityDTO getElement() {
        return element;
    }

    public void setElement(EntityDTO element) {
        this.element = element;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }
}
