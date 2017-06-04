package org.hale.weaver.services;

import org.hale.commons.io.Sink;
import org.neo4j.ogm.model.Result;
import org.neo4j.ogm.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by guilherme on 5/27/17.
 * hale
 */
public class QueryRunner {

    private final Logger logger = LoggerFactory.getLogger(QueryRunner.class);

    Session session;

    public QueryRunner(Session session){
        this.session = session;
    }

    public void run(Query query, Sink sink){
        logger.info(query.getTemplate());
        Result result = session.query(query.getTemplate(), query.getParameters());
        Stream<Map<String, Object>> resultStream = StreamSupport.stream(result.spliterator(), false);
        sink.write(resultStream.map(e -> query.parseResult(e)).iterator());
    }
}
