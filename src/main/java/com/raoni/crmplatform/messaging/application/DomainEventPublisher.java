package com.raoni.crmplatform.messaging.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class DomainEventPublisher {
    private static final Logger log = LoggerFactory.getLogger(DomainEventPublisher.class);

    public void publish(String eventType, String aggregateId) {
        log.info("domain event type={} aggregateId={}", eventType, aggregateId);
    }
}
