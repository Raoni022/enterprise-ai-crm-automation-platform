package com.raoni.crmplatform.leads.api;

import com.raoni.crmplatform.leads.application.LeadWorkflowService;
import com.raoni.crmplatform.leads.dto.CreateLeadRequest;
import com.raoni.crmplatform.leads.dto.LeadResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/leads")
public class LeadController {
    private final LeadWorkflowService leadWorkflowService;

    public LeadController(LeadWorkflowService leadWorkflowService) {
        this.leadWorkflowService = leadWorkflowService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public LeadResponse create(
            @RequestHeader("Idempotency-Key") String idempotencyKey,
            @Valid @RequestBody CreateLeadRequest request
    ) {
        return leadWorkflowService.intake(idempotencyKey, request);
    }

    @GetMapping("/{leadId}")
    public LeadResponse get(@PathVariable UUID leadId) {
        return leadWorkflowService.find(leadId);
    }

    @GetMapping
    public List<LeadResponse> list() {
        return leadWorkflowService.findAll();
    }

    @PostMapping("/{leadId}/qualify")
    public LeadResponse qualify(@PathVariable UUID leadId) {
        return leadWorkflowService.qualify(leadId);
    }
}
