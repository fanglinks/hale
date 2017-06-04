package org.hale.commons.io;

import java.util.Iterator;

/**
 * Created by guilherme on 5/27/17.
 * hale
 */
public interface Sink {
    void write(Iterator<Object> stream);
}
