package org.hale.weaver.services.meta;

import org.hale.weaver.services.Query;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by guilherme on 5/27/17.
 * hale
 */
public class ListEventTypeQuery implements Query<String> {

    public ListEventTypeQuery() {
    }

    @Override
    public String getTemplate() {
        String template = "" +
                "MATCH (:Entity)-[:AGENT_EVENT]->(n :Event)-[:EVENT_ELEMENT]->(:Entity)" +
                "\nRETURN DISTINCT (n.type) AS type" +
                "\nORDER BY type";
        return template;
    }

    @Override
    public Map<String, Object> getParameters() {
        Map<String, Object> params = new HashMap<>();
        return params;
    }

    @Override
    public String parseResult(Map<String, Object> result) {
        return (String)result.get("type");
    }
}
