package org.hale;

import org.junit.After;
import org.junit.Before;
import org.hale.commons.io.neo4j.Neo4jSessionFactory;
import org.neo4j.ogm.session.Session;

import static org.junit.Assert.assertNotNull;

/**
 * Created by guilherme on 4/29/17.
 */
public class RepositoryTest {

    protected Session session;

    @Before
    public void setUpRepository() {
        session = Neo4jSessionFactory.getInstance().getNeo4jSession();
        assertNotNull(session);
    }

    @After
    public void tearDownDatabase() throws Exception {
        session.purgeDatabase();
        session.clear();
    }

}
