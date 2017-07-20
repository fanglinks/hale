package org.hale.weaver.services;

/**
 * Created by guilherme on 6/4/17.
 * hale
 */
public class EntityCount {
    private String id;
    private long count;

    public EntityCount(){}

    public EntityCount(String id, long count) {
        this.id = id;
        this.count = count;
    }

    public String getId() {
        return id;
    }

    public EntityCount setId(String id) {
        this.id = id;
        return this;
    }

    public long getCount() {
        return count;
    }

    public EntityCount setCount(long count) {
        this.count = count;
        return this;
    }

    public String toString(){
        return String.format(
                "EntityCount(id: %s, count: %d)", this.id, this.count
        );
    }
}
