package org.hale;

import org.hale.commons.io.console.ConsoleSink;
import org.hale.commons.io.neo4j.Neo4jSessionFactory;
import org.hale.weaver.services.Query;
import org.hale.weaver.services.QueryRunner;
import org.hale.weaver.services.domain.ElementBasedRanking;
import org.neo4j.ogm.session.Session;

/**
 * Created by guilherme on 6/4/17.
 * hale
 */
public class App {

    public static void main(String[] args){
        Session session =  Neo4jSessionFactory.getInstance().getNeo4jSession();

        Query query = new ElementBasedRanking("product", "item-402", "user", "(.*?)", 10);
        QueryRunner queryRunner = new QueryRunner(session);
        queryRunner.run(query, new ConsoleSink());
    }
}
