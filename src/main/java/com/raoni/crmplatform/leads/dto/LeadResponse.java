package com.raoni.crmplatform.leads.dto;

import com.raoni.crmplatform.leads.domain.Lead;
import com.raoni.crmplatform.leads.domain.LeadStatus;
import com.raoni.crmplatform.leads.domain.QualificationTier;

import java.time.Instant;
import java.util.UUID;

public record LeadResponse(
        UUID id,
        String fullName,
        String email,
        String company,
        String country,
        String source,
        LeadStatus status,
        int score,
        QualificationTier tier,
        String enrichmentSummary,
        Instant createdAt
) {
    public static LeadResponse from(Lead lead) {
        return new LeadResponse(
                lead.getId(),
                lead.getFullName(),
                lead.getEmail(),
                lead.getCompany(),
                lead.getCountry(),
                lead.getSource(),
                lead.getStatus(),
                lead.getScore(),
                lead.getTier(),
                lead.getEnrichmentSummary(),
                lead.getCreatedAt()
        );
    }
}
