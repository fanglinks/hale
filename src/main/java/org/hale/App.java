package org.hale;

import org.hale.commons.io.Sink;
import org.hale.commons.io.console.ConsoleSink;
import org.hale.commons.io.neo4j.Neo4jSessionFactory;
import org.hale.weaver.services.Query;
import org.hale.weaver.services.QueryRunner;
import org.hale.weaver.services.domain.SimilarAgentRanking;
import org.hale.weaver.services.domain.SimilarElementRanking;
import org.hale.weaver.services.meta.TypeLabelsQuery;
import org.neo4j.ogm.session.Session;

/**
 * Created by guilherme on 6/4/17.
 * hale
 */
public class App {

    public static void main(String[] args){
        Session session =  Neo4jSessionFactory.getInstance().getNeo4jSession();

        Sink consoleSink = new ConsoleSink();

        Query query = new SimilarElementRanking("product", "item-402", "user", "(.*?)", 0, 10);
        QueryRunner queryRunner = new QueryRunner(session);
        queryRunner.run(query, consoleSink);

        query = new SimilarAgentRanking("user", "user-17525", "product", "(.*?)",0,  10);
        queryRunner = new QueryRunner(session);
        queryRunner.run(query, consoleSink);

        query = new TypeLabelsQuery();
        queryRunner = new QueryRunner(session);
        queryRunner.run(query, consoleSink);
    }
}
