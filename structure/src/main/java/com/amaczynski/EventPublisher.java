package com.amaczynski;

import java.util.List;

public interface EventPublisher {

    void publish(PublishedEvent event);

    void publish(List<PublishedEvent> events);
}
