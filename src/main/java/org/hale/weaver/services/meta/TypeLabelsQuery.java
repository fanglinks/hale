package org.hale.weaver.services.meta;

import org.hale.weaver.services.Query;
import org.hale.weaver.services.domain.EntityCount;

import java.util.*;

/**
 * Created by guilherme on 5/27/17.
 * hale
 */
public class TypeLabelsQuery implements Query<String> {

    public TypeLabelsQuery() {
    }

    @Override
    public String getTemplate() {
        String template = "" +
                "MATCH (n)" +
                "\nWHERE NOT (:Entity)-[:AGENT_EVENT]->(n)-[:EVENT_ELEMENT]->(:Entity)" +
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
