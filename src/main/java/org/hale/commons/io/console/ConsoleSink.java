package org.hale.commons.io.console;

import org.hale.commons.io.Sink;

import java.io.Serializable;
import java.util.Iterator;

/**
 * Created by guilherme on 6/4/17.
 * hale
 */
public class ConsoleSink implements Sink {

    @Override
    public void write(Iterator<Object> stream) {
        stream.forEachRemaining(e -> System.out.println(e.toString()));
    }
}
