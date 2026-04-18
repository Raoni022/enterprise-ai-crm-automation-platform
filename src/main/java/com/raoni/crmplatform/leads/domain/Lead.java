package com.raoni.crmplatform.leads.domain;

import java.time.Instant;
import java.util.UUID;

public class Lead {
    private final UUID id;
    private final String idempotencyKey;
    private final String fullName;
    private final String email;
    private final String company;
    private final String country;
    private final String source;
    private final String message;
    private LeadStatus status;
    private int score;
    private QualificationTier tier;
    private String enrichmentSummary;
    private final Instant createdAt;

    public Lead(String idempotencyKey, String fullName, String email, String company, String country, String source, String message) {
        this.id = UUID.randomUUID();
        this.idempotencyKey = idempotencyKey;
        this.fullName = fullName;
        this.email = email;
        this.company = company;
        this.country = country;
        this.source = source;
        this.message = message;
        this.status = LeadStatus.NEW;
        this.score = 0;
        this.tier = QualificationTier.COLD;
        this.enrichmentSummary = "not_enriched";
        this.createdAt = Instant.now();
    }

    public void enrich(String enrichmentSummary) {
        this.enrichmentSummary = enrichmentSummary;
        this.status = LeadStatus.ENRICHED;
    }

    public void qualify(int score, QualificationTier tier) {
        this.score = score;
        this.tier = tier;
        this.status = LeadStatus.QUALIFIED;
    }

    public void markSynced() {
        this.status = LeadStatus.SYNCED_TO_CRM;
    }

    public UUID getId() { return id; }
    public String getIdempotencyKey() { return idempotencyKey; }
    public String getFullName() { return fullName; }
    public String getEmail() { return email; }
    public String getCompany() { return company; }
    public String getCountry() { return country; }
    public String getSource() { return source; }
    public String getMessage() { return message; }
    public LeadStatus getStatus() { return status; }
    public int getScore() { return score; }
    public QualificationTier getTier() { return tier; }
    public String getEnrichmentSummary() { return enrichmentSummary; }
    public Instant getCreatedAt() { return createdAt; }
}
