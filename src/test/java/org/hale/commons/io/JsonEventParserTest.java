package org.hale.commons.io;

import org.json.JSONException;
import org.junit.Test;
import org.hale.commons.io.json.JsonEventParser;
import org.hale.commons.types.basic.Event;

import java.util.Arrays;

import static org.junit.Assert.*;

/**
 * Created on 2017-03-22
 * hale
 *
 * @author guilherme
 */
public class JsonEventParserTest {

    @Test
    public void testShouldParseSingleEventBuffer(){

        String data = "{\n" +
                "  \"type\": \"view\",\n" +
                "  \"timestamp\": 1234567890,\n" +
                "  \"context\": \"session-17\",\n" +
                "  \"agent\": {\n" +
                "    \"id\": \"user-11\",\n" +
                "    \"type\": \"user\"\n" +
                "  },\n" +
                "  \"element\": {\n" +
                "    \"id\": \"asset-15\",\n" +
                "    \"type\": \"book\"\n" +
                "  }\n" +
                "}";

        JsonEventParser jsonEventParser = new JsonEventParser();

        Event event = jsonEventParser.parse(data);

        assertNotNull(event);
        assertEquals("view", event.getType());
        assertEquals(new Long(1234567890), event.getTimestamp());
        assertEquals("session-17", event.getContext());

        assertNotNull(event.getAgent());
        assertEquals("user-11", event.getAgent().getId());
        assertEquals("user", event.getAgent().getType());

        assertNotNull(event.getElement());
        assertEquals("asset-15", event.getElement().getId());
        assertEquals("book", event.getElement().getType());
    }

    @Test
    public void testShouldParseMultipleEventBuffers(){

        JsonEventParser jsonEventParser = new JsonEventParser();

        String data = "{\n" +
                "  \"type\": \"view\",\n" +
                "  \"timestamp\": 1234567890,\n" +
                "  \"context\": \"session-17\",\n" +
                "  \"agent\": {\n" +
                "    \"id\": \"user-11\",\n" +
                "    \"type\": \"user\"\n" +
                "  },\n" +
                "  \"element\": {\n" +
                "    \"id\": \"asset-15\",\n" +
                "    \"type\": \"book\"\n" +
                "  }\n" +
                "}";

        Iterable<Event> events = jsonEventParser.parse(Arrays.asList(data, data));

        for(Event event: events) {
            assertNotNull(event);
            assertEquals("view", event.getType());
            assertEquals(new Long(1234567890), event.getTimestamp());
            assertEquals("session-17", event.getContext());

            assertNotNull(event.getAgent());
            assertEquals("user-11", event.getAgent().getId());
            assertEquals("user", event.getAgent().getType());

            assertNotNull(event.getElement());
            assertEquals("asset-15", event.getElement().getId());
            assertEquals("book", event.getElement().getType());
        }
    }

    @Test
    public void testShouldParseEventWithMissingOptionalField(){
        String data = "{\n" +
                "  \"type\": \"view\",\n" +
                "  \"agent\": {\n" +
                "    \"id\": \"user-11\",\n" +
                "    \"type\": \"user\"\n" +
                "  },\n" +
                "  \"element\": {\n" +
                "    \"id\": \"asset-15\",\n" +
                "    \"type\": \"book\"\n" +
                "  }\n" +
                "}";

        JsonEventParser jsonEventParser = new JsonEventParser();

        Event event = jsonEventParser.parse(data);

        assertNotNull(event);
        assertEquals("view", event.getType());
        assertNull(event.getContext());
        assertNull(event.getTimestamp());

        assertNotNull(event.getAgent());
        assertEquals("user-11", event.getAgent().getId());
        assertEquals("user", event.getAgent().getType());

        assertNotNull(event.getElement());
        assertEquals("asset-15", event.getElement().getId());
        assertEquals("book", event.getElement().getType());
    }

    @Test(expected = JSONException.class)
    public void testShouldFailtToParseEventWithMissingMandatoryField(){

        // missing `event:name` field
        String data = "{\n" +
                "  \"id\": \"12345\",\n" +
                "  \"context\": \"session-17\",\n" +
                "  \"agent\": {\n" +
                "    \"id\": \"user-11\",\n" +
                "    \"type\": \"user\"\n" +
                "  },\n" +
                "  \"element\": {\n" +
                "    \"id\": \"asset-15\",\n" +
                "    \"type\": \"book\"\n" +
                "  }\n" +
                "}";

        JsonEventParser jsonEventParser = new JsonEventParser();

        jsonEventParser.parse(data);
    }
}
