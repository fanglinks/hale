package org.hale.lens.server;

import com.fasterxml.jackson.jaxrs.json.JacksonJaxbJsonProvider;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by guilherme on 7/12/17.
 * hale
 */
public class JerseyApplication extends ResourceConfig {
    private final Logger logger = LoggerFactory.getLogger(JerseyApplication.class);

    public JerseyApplication() {
        logger.info("Setting up hk2");
        packages("org.hale.lens.server", "org.hale.lens.api");

        JacksonJaxbJsonProvider jacksonJaxbJsonProvider = new JacksonJaxbJsonProvider();
        jacksonJaxbJsonProvider.setMapper(new ObjectMapperFactory().buildObjectMapper());
        register(jacksonJaxbJsonProvider);
        register(new AbstractBinder() {
            @Override
            protected void configure() {

                // Shop to manually bind objects, in the case that the Jersey Auto-scan isn't working
                // e.g. bind(x.class).to(y.class).in(Singleton.class);
                // e.g. bind(x.class).to(y.class);
                //
                // note: if the object is generic, use TypeLiteral
                // e.g. bind(x.class).to(new TypeLiteral&lt;InjectionResolver&gt;(){});
                //
            }
        });
    }
}