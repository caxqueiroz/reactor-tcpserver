package io.cax.debs2016;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import reactor.spring.context.annotation.Selector;

/**
 * Created by cax on 20/01/2016.
 */
@Consumer
public class EventProcessor {

    @Autowired
    private Logger log;

    @Autowired
    public EventBus eventBus;

    @Selector("debs2016.topic")
    public void onTopic(String s) {
        log.info("onTopic: {}", s);
    }
}
