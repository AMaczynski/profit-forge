package com.amaczynski;

import java.util.List;

public interface EventPublisher {

    void publish(PublishedEvent event);

    default void publish(List<PublishedEvent> events) {
        events.forEach(this::publish);
    }
}
