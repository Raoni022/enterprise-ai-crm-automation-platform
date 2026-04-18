package com.raoni.crmplatform.leads.application;

import com.raoni.crmplatform.leads.domain.Lead;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class LeadStore {
    private final Map<UUID, Lead> leads = new ConcurrentHashMap<>();
    private final Map<String, UUID> idempotencyIndex = new ConcurrentHashMap<>();

    public Lead save(Lead lead) {
        leads.put(lead.getId(), lead);
        idempotencyIndex.put(lead.getIdempotencyKey(), lead.getId());
        return lead;
    }

    public Optional<Lead> findById(UUID id) {
        return Optional.ofNullable(leads.get(id));
    }

    public Optional<Lead> findByIdempotencyKey(String key) {
        UUID id = idempotencyIndex.get(key);
        return id == null ? Optional.empty() : findById(id);
    }

    public List<Lead> findAll() {
        return leads.values().stream().toList();
    }
}
