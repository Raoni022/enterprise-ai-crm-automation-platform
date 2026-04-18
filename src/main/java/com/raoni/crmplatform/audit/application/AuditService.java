package com.raoni.crmplatform.audit.application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuditService {
    private static final Logger log = LoggerFactory.getLogger(AuditService.class);

    public void record(String eventType, String aggregateId, String details) {
        log.info("audit eventType={} aggregateId={} details={}", eventType, aggregateId, details);
    }
}
