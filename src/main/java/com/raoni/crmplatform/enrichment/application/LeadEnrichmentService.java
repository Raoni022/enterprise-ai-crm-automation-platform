package com.raoni.crmplatform.enrichment.application;

import com.raoni.crmplatform.leads.domain.Lead;
import org.springframework.stereotype.Service;

@Service
public class LeadEnrichmentService {
    public String enrich(Lead lead) {
        String market = switch (lead.getCountry().toLowerCase()) {
            case "canada" -> "north_america_canada";
            case "united states", "usa", "us" -> "north_america_us";
            case "brazil", "brasil" -> "latin_america_brazil";
            default -> "global";
        };
        return "market=" + market + "; company=" + lead.getCompany() + "; source=" + lead.getSource();
    }
}
