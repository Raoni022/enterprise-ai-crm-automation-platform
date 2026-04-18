package com.raoni.crmplatform.integrations.notification;

import com.raoni.crmplatform.leads.domain.Lead;
import org.springframework.stereotype.Component;

@Component
public class NotificationAdapter {
    public String draftFollowUp(Lead lead) {
        return "Drafted follow-up for " + lead.getFullName() + " at " + lead.getCompany();
    }
}
