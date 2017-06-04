package org.hale.commons.io;

/**
 * Created on 2017-03-20.
 * hale
 *
 * Reads bytes from a stream. A byte sequence should represent an element (actor, event, asset)
 *
 * @author guilherme
 */
public interface StreamReader {

    /**
     * Retrieves the next byte sequence as a string from a stream. Should this the stream be closed, this should raise an runtime exception.
     *
     * @return returns byte sequence as a string, if stream is open.
     */
    String next();

    /**
     * Checks if the stream is closed. If so, then no more data can be retrieved.
     *
     * @return true is the stream is closed, and false otherwise.
     */
    boolean isClosed();
}
