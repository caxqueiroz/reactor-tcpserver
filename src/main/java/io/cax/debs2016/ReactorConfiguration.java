package io.cax.debs2016;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import reactor.Environment;
import reactor.bus.EventBus;
import reactor.spring.context.config.EnableReactor;

/**
 * Created by cax on 20/01/2016.
 */
@Configuration
@ComponentScan
@EnableReactor
public class ReactorConfiguration {

    @Bean public EventBus eventBus() {
        Environment env = Environment.initialize();
        return EventBus.create(Environment.get());
    }

    @Bean
    public Logger log() {
        return LoggerFactory.getLogger(Debs2016Application.class);
    }

}