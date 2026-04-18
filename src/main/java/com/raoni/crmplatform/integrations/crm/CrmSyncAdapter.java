package com.raoni.crmplatform.integrations.crm;

import com.raoni.crmplatform.leads.domain.Lead;
import org.springframework.stereotype.Component;

@Component
public class CrmSyncAdapter {
    public String sync(Lead lead) {
        return "mock-crm-id-" + lead.getId().toString().substring(0, 8);
    }
}
