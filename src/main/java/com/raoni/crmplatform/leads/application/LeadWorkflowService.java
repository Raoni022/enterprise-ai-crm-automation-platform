package com.raoni.crmplatform.leads.application;

import com.raoni.crmplatform.audit.application.AuditService;
import com.raoni.crmplatform.enrichment.application.LeadEnrichmentService;
import com.raoni.crmplatform.integrations.crm.CrmSyncAdapter;
import com.raoni.crmplatform.integrations.notification.NotificationAdapter;
import com.raoni.crmplatform.leads.domain.Lead;
import com.raoni.crmplatform.leads.dto.CreateLeadRequest;
import com.raoni.crmplatform.leads.dto.LeadResponse;
import com.raoni.crmplatform.messaging.application.DomainEventPublisher;
import com.raoni.crmplatform.qualification.application.LeadQualificationService;
import com.raoni.crmplatform.qualification.dto.QualificationResult;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LeadWorkflowService {
    private final LeadStore leadStore;
    private final LeadEnrichmentService enrichmentService;
    private final LeadQualificationService qualificationService;
    private final CrmSyncAdapter crmSyncAdapter;
    private final NotificationAdapter notificationAdapter;
    private final AuditService auditService;
    private final DomainEventPublisher eventPublisher;

    public LeadWorkflowService(
            LeadStore leadStore,
            LeadEnrichmentService enrichmentService,
            LeadQualificationService qualificationService,
            CrmSyncAdapter crmSyncAdapter,
            NotificationAdapter notificationAdapter,
            AuditService auditService,
            DomainEventPublisher eventPublisher
    ) {
        this.leadStore = leadStore;
        this.enrichmentService = enrichmentService;
        this.qualificationService = qualificationService;
        this.crmSyncAdapter = crmSyncAdapter;
        this.notificationAdapter = notificationAdapter;
        this.auditService = auditService;
        this.eventPublisher = eventPublisher;
    }

    public LeadResponse intake(String idempotencyKey, CreateLeadRequest request) {
        if (idempotencyKey == null || idempotencyKey.isBlank()) {
            throw new IllegalArgumentException("Idempotency-Key header is required");
        }

        return leadStore.findByIdempotencyKey(idempotencyKey)
                .map(LeadResponse::from)
                .orElseGet(() -> createAndProcessLead(idempotencyKey, request));
    }

    public LeadResponse qualify(UUID leadId) {
        Lead lead = findLead(leadId);
        QualificationResult result = qualificationService.qualify(lead);
        lead.qualify(result.score(), result.tier());
        String crmId = crmSyncAdapter.sync(lead);
        lead.markSynced();
        String notificationDraft = notificationAdapter.draftFollowUp(lead);
        auditService.record("LEAD_QUALIFIED", lead.getId().toString(), "score=" + result.score() + ",tier=" + result.tier() + ",crmId=" + crmId + ",notification=" + notificationDraft);
        eventPublisher.publish("lead.qualified", lead.getId().toString());
        return LeadResponse.from(lead);
    }

    public LeadResponse find(UUID leadId) {
        return LeadResponse.from(findLead(leadId));
    }

    public List<LeadResponse> findAll() {
        return leadStore.findAll().stream().map(LeadResponse::from).toList();
    }

    private LeadResponse createAndProcessLead(String idempotencyKey, CreateLeadRequest request) {
        Lead lead = new Lead(
                idempotencyKey,
                request.fullName(),
                request.email(),
                request.company(),
                request.country(),
                request.source(),
                request.message()
        );
        lead.enrich(enrichmentService.enrich(lead));
        leadStore.save(lead);
        auditService.record("LEAD_INTAKE", lead.getId().toString(), "source=" + lead.getSource());
        eventPublisher.publish("lead.created", lead.getId().toString());
        return LeadResponse.from(lead);
    }

    private Lead findLead(UUID leadId) {
        return leadStore.findById(leadId)
                .orElseThrow(() -> new IllegalArgumentException("Lead not found: " + leadId));
    }
}
