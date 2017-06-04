package org.hale.weaver.repository;

import org.junit.Test;
import org.hale.RepositoryTest;
import org.hale.commons.types.domain.Entity;
import org.hale.commons.types.domain.Event;
import org.hale.weaver.repository.domain.EventRepository;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.ZoneOffset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * Created by guilherme on 5/14/17.
 */
public class EventRepositoryTest extends RepositoryTest {

    @Test
    public void testShouldCRUDEvent() {

        EventRepository eventRepository = new EventRepository(this.session);

        Entity actor = new Entity("A01", "Sandy Rodriguez");
        Entity asset = new Entity("M01", "Days of Sun");
        Event event = new Event(actor, "actedIn", asset,
                LocalDateTime.of(2015, Month.APRIL, 6, 0, 0).toEpochSecond(ZoneOffset.UTC),
                1.0,
                null);

        assertFalse(eventRepository.exists(event));

        eventRepository.save(event);
        Event createdEvent = eventRepository.find(event);
        assertNotNull(createdEvent);
        assertEquals(event, createdEvent);
        assertEquals(event.getTimestamp(), createdEvent.getTimestamp());
        assertEquals(event.getWeight(), createdEvent.getWeight());
        assertNotNull(event.getAgent());
        assertEquals(event.getAgent(), createdEvent.getAgent());
        assertEquals(event.getElement(), createdEvent.getElement());
    }
}
