package org.hale.commons.io;

import org.hale.commons.types.basic.Event;

/**
 * Created on 2017-03-22
 * hale
 *
 * Interface for event parsers
 *
 * @author guilherme
 */
public interface EventParser {

    /**
     * Parses an event from an event buffer
     *
     * @param buffer JSON object
     * @return An event, if successfully parsed
     */
    Event parse(String buffer);

    /**
     * Parses several events from an iterable object of event buffers
     *
     * @param buffers JSON objects
     * @return Iterable of parsed events, if successfully parsed
     */
    Iterable<Event> parse(Iterable<String> buffers);
}
