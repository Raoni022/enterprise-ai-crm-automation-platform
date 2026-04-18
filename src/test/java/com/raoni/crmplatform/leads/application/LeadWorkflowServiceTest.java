package com.raoni.crmplatform.leads.application;

import com.raoni.crmplatform.audit.application.AuditService;
import com.raoni.crmplatform.enrichment.application.LeadEnrichmentService;
import com.raoni.crmplatform.integrations.crm.CrmSyncAdapter;
import com.raoni.crmplatform.integrations.notification.NotificationAdapter;
import com.raoni.crmplatform.leads.domain.LeadStatus;
import com.raoni.crmplatform.leads.dto.CreateLeadRequest;
import com.raoni.crmplatform.messaging.application.DomainEventPublisher;
import com.raoni.crmplatform.qualification.application.LeadQualificationService;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LeadWorkflowServiceTest {
    @Test
    void intakeIsIdempotentForSameKey() {
        LeadWorkflowService service = newService();
        CreateLeadRequest request = new CreateLeadRequest(
                "Alex Morgan",
                "alex@northstar.com",
                "Northstar Logistics",
                "Canada",
                "website",
                "We need CRM automation and pricing."
        );

        var first = service.intake("same-key", request);
        var second = service.intake("same-key", request);

        assertThat(second.id()).isEqualTo(first.id());
        assertThat(first.status()).isEqualTo(LeadStatus.ENRICHED);
    }

    @Test
    void qualifiesAndSyncsLead() {
        LeadWorkflowService service = newService();
        var lead = service.intake("qualify-key", new CreateLeadRequest(
                "Alex Morgan",
                "alex@northstar.com",
                "Northstar Logistics",
                "Canada",
                "website",
                "We need CRM automation and pricing."
        ));

        var qualified = service.qualify(lead.id());

        assertThat(qualified.status()).isEqualTo(LeadStatus.SYNCED_TO_CRM);
        assertThat(qualified.score()).isGreaterThan(0);
    }

    private LeadWorkflowService newService() {
        return new LeadWorkflowService(
                new LeadStore(),
                new LeadEnrichmentService(),
                new LeadQualificationService(75, 45),
                new CrmSyncAdapter(),
                new NotificationAdapter(),
                new AuditService(),
                new DomainEventPublisher()
        );
    }
}
