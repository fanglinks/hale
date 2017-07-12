package org.hale.commons.io.collection;

import org.hale.commons.io.Sink;

import java.util.Iterator;
import java.util.List;

/**
 * Created by guilherme on 7/13/17.
 * hale
 */
public class ListSink<T> implements Sink {

    List<T> list;

    public ListSink(List<T> list){
        this.list = list;
    }

    @Override
    public void write(Iterator<Object> stream) {
        stream.forEachRemaining(e -> list.add((T)e));
    }
}
