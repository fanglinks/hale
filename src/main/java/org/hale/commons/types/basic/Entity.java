package org.hale.commons.types.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hale.commons.types.Constants;
import org.neo4j.ogm.annotation.GraphId;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.NodeEntity;
import org.neo4j.ogm.annotation.Relationship;

import java.util.List;

/**
 * Created by guilherme on 2017-03-20.
 */
@NodeEntity(label = Constants.ENTITY)
public class Entity {

    @GraphId
    @JsonIgnore
    private Long graphId;

    @Index
    private String id;

    @Index
    private String type;

    public Entity(){

    }

    public Entity(String id, String type) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Entity)) return false;

        Entity type1 = (Entity) o;

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
