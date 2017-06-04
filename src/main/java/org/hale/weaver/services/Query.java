package org.hale.weaver.services;

import java.util.Map;
import java.util.function.Function;

/**
 * Created by guilherme on 5/27/17.
 * hale
 */
public interface Query<T> {
    String getTemplate();
    Map<String, Object> getParameters();
    T parseResult(Map<String, Object> result);
}
