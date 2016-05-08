package io.cax.reactor;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import reactor.bus.Event;
import reactor.bus.EventBus;
import reactor.spring.context.annotation.Consumer;
import javax.annotation.PostConstruct;

import static reactor.bus.selector.Selectors.$;

/**
 * Created by cax on 20/01/2016.
 */
@Consumer
public class EventProcessor {

    @Autowired
    private Logger log;

    @Value("${topic.name}")
    private String topicName;

    @Autowired
    public EventBus eventBus;

    private void onTopic(String s) {
        log.info("onTopic: {}", s);
    }


    @PostConstruct
    private void register() {
        eventBus.on($(topicName), (Event<String> ev) -> {
            onTopic(ev.getData());
        });
    }
}
