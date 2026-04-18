package com.raoni.crmplatform.integrations.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/integrations")
public class IntegrationStatusController {
    @GetMapping("/status")
    public Map<String, String> status() {
        return Map.of(
                "crm", "mock_ready",
                "notification", "mock_ready",
                "events", "in_memory_ready"
        );
    }
}
