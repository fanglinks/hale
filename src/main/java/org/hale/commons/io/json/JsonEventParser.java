package org.hale.commons.io.json;

import org.json.JSONObject;
import org.hale.commons.io.EventParser;
import org.hale.commons.types.domain.Entity;
import org.hale.commons.types.domain.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created on 2017-03-22
 * hale
 *
 * @author guilherme
 */
public class JsonEventParser implements EventParser {

    public final static String KEY_ID = "id";
    public final static String KEY_TYPE = "type";
    public final static String KEY_TIMESTAMP = "timestamp";
    public final static String KEY_WEIGHT = "weight";
    public final static String KEY_CONTEXT = "context";
    public final static String KEY_AGENT = "agent";
    public final static String KEY_ELEMENT = "element";


    /**
     * Parses an event from an event buffer
     *
     * @param buffer JSON object
     * @return An event, if successfully parsed
     */
    @Override
    public Event parse(String buffer) {

        JSONObject root = new JSONObject(buffer);
        Set<String> rootKeySet = root.keySet();
        JSONObject actorObject = root.getJSONObject(KEY_AGENT);
        JSONObject assetObject = root.getJSONObject(KEY_ELEMENT);

        Entity actor = new Entity(actorObject.getString(KEY_ID), actorObject.getString(KEY_TYPE));
        Entity asset = new Entity(assetObject.getString(KEY_ID), assetObject.getString(KEY_TYPE));
        Event event = new Event(actor, root.getString(KEY_TYPE), asset);

        for(String key: rootKeySet){
            switch (key){
                case KEY_TIMESTAMP:
                    if (!root.isNull(KEY_TIMESTAMP)) event.setTimestamp(root.getLong(KEY_TIMESTAMP));
                    break;
                case KEY_CONTEXT:
                    event.setContext(root.getString(KEY_CONTEXT));
                    break;
                case KEY_WEIGHT:
                    if (!root.isNull(KEY_WEIGHT)) event.setWeight(root.getDouble(KEY_WEIGHT));
            }
        }

        event.setAgent(actor);
        event.setElement(asset);

        return event;
    }

    /**
     * Parses several events from a list of event buffers
     *
     * @param buffers JSON objects
     * @return List of parsed events, if successfully parsed
     */
    @Override
    public Iterable<Event> parse(Iterable<String> buffers) {

        List<Event> events = new ArrayList<>();

        for(String buffer: buffers){
            events.add(parse(buffer));
        }

        return events;
    }
}
