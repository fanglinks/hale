package org.hale.weaver.repository;

import org.junit.Test;
import org.hale.RepositoryTest;
import org.hale.commons.types.basic.Entity;
import org.hale.weaver.repository.domain.EntityRepository;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.*;

/**
 * Created by guilherme on 4/3/17.
 */
public class EntityRepositoryTest extends RepositoryTest {

    @Test
    public void testShouldCRUDEntity() {

        EntityRepository entityRepository = new EntityRepository(session);
        Entity entity = new Entity("1", "Author");

        entityRepository.save(entity);
        assertTrue(entityRepository.exists(entity));

        Entity savedEntity = entityRepository.get(entity);

        assertNotNull(savedEntity);
        assertEquals(entity.getId(), savedEntity.getId());
        assertEquals(entity.getType(), savedEntity.getType());

        Entity foundEntity = entityRepository.find(entity);
        assertNotNull(foundEntity);
        assertEquals(entity.getId(), foundEntity.getId());
        assertEquals(entity.getType(), foundEntity.getType());

        entityRepository.delete(entity);
        assertFalse(entityRepository.exists(entity));
        assertNull(entityRepository.find(entity));
    }

    @Test(expected = java.util.NoSuchElementException.class)
    public void testShouldFailToRetrieveNonExistingEntity() {
        EntityRepository entityRepository = new EntityRepository(session);
        Entity entity = new Entity("1", "Author");

        entityRepository.get(entity);
    }
}
