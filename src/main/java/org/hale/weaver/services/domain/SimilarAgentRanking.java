package org.hale.weaver.services.domain;

import org.hale.weaver.services.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guilherme on 5/27/17.
 * hale
 */
public class SimilarAgentRanking implements Query<EntityCount> {

    private String elementType;
    private String agentId;
    private String agentType;
    private String eventType;
    private int limit;

    public SimilarAgentRanking(String agentType, String agentId, String elementType, String eventType, int limit) {
        this.agentType = agentType;
        this.agentId = agentId;
        this.elementType = elementType;
        this.eventType = eventType;
        this.limit = limit;
    }

    @Override
    public String getTemplate() {

        String template = "" +
                "MATCH (agent :Entity {type: {agentType}, id: {agentId}})-[:AGENT_EVENT]->(event)-[:EVENT_ELEMENT]->(element :Entity {type: {elementType}})" +
                "\nWHERE event.type =~ {eventType}" +
                "\nWITH agent, event, element" +
                "\nMATCH (otherAgent)-[:AGENT_EVENT]->(otherEvent)-[:EVENT_ELEMENT]->(element)" +
                "\nWHERE otherEvent.type =~ {eventType} AND event <> otherEvent AND agent <> otherAgent" +
                "\nRETURN otherAgent.id AS id, COUNT(otherEvent) AS count" +
                "\nORDER BY count DESC" +
                "\nLIMIT {limit}";
        return template;
    }

    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        params.put("agentType", this.agentType);
        params.put("eventType", this.eventType);
        params.put("elementType", this.elementType);
        params.put("agentId", this.agentId);
        params.put("limit", this.limit);
        return params;
    }

    @Override
    public EntityCount parseResult(Map<String, Object> result) {
        return new EntityCount((String)result.get("id"), (Long)result.get("count"));
    }
}
