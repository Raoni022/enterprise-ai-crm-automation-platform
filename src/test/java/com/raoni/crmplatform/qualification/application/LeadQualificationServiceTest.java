package com.raoni.crmplatform.qualification.application;

import com.raoni.crmplatform.leads.domain.Lead;
import com.raoni.crmplatform.leads.domain.QualificationTier;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LeadQualificationServiceTest {
    private final LeadQualificationService service = new LeadQualificationService(75, 45);

    @Test
    void classifiesHighIntentLeadAsHot() {
        Lead lead = new Lead(
                "key-1",
                "Alex Morgan",
                "alex@northstar.com",
                "Northstar Logistics",
                "Canada",
                "website",
                "We need CRM automation and pricing for our sales team."
        );

        var result = service.qualify(lead);

        assertThat(result.tier()).isEqualTo(QualificationTier.HOT);
        assertThat(result.score()).isGreaterThanOrEqualTo(75);
        assertThat(result.reasons()).contains("automation_intent", "crm_relevance", "commercial_signal");
    }

    @Test
    void classifiesWeakLeadAsCold() {
        Lead lead = new Lead(
                "key-2",
                "Visitor",
                "visitor@gmail.com",
                "Unknown",
                "Spain",
                "blog",
                "Just browsing."
        );

        var result = service.qualify(lead);

        assertThat(result.tier()).isEqualTo(QualificationTier.COLD);
    }
}
