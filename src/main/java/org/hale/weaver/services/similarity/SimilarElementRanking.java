package org.hale.weaver.services.similarity;

import org.hale.weaver.services.Query;
import org.hale.weaver.services.EntityCount;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guilherme on 5/27/17.
 * hale
 */
public class SimilarElementRanking implements Query<EntityCount> {

    private String elementType;
    private String elementId;
    private String agentType;
    private String eventType;
    private int skip;
    private int limit;

    public SimilarElementRanking(String elementType, String elementId, String agentType, String eventType, int skip, int limit) {
        this.elementType = elementType;
        this.elementId = elementId;
        this.agentType = agentType;
        this.eventType = eventType;
        this.skip = skip;
        this.limit = limit;
    }

    @Override
    public String getTemplate() {
        String template = "" +
                "MATCH (agent :Entity {type: {agentType}})-[:AGENT_EVENT]->(event)-[:EVENT_ELEMENT]->(element :Entity {type: {elementType}, id: {elementId}})" +
                "\nWHERE event.type =~ {eventType}" +
                "\nWITH agent, event, element" +
                "\nMATCH (agent)-[:AGENT_EVENT]->(otherEvent)-[:EVENT_ELEMENT]->(otherElement :Entity {type: {elementType}})" +
                "\nWHERE otherEvent.type =~ {eventType} AND event <> otherEvent AND element <> otherElement" +
                "\nRETURN otherElement.id AS id, COUNT(otherEvent) AS count" +
                "\nORDER BY count DESC" +
                "\nSKIP {skip}" +
                "\nLIMIT {limit}";
        return template;
    }

    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("agentType", this.agentType);
        params.put("eventType", this.eventType);
        params.put("elementType", this.elementType);
        params.put("elementId", this.elementId);
        params.put("skip", this.skip);
        params.put("limit", this.limit);
        return params;
    }

    @Override
    public EntityCount parseResult(Map<String, Object> result) {
        return new EntityCount((String) result.get("id"), (Long) result.get("count"));
    }
}
