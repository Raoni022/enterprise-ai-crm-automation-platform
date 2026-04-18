package com.raoni.crmplatform.qualification.application;

import com.raoni.crmplatform.leads.domain.Lead;
import com.raoni.crmplatform.leads.domain.QualificationTier;
import com.raoni.crmplatform.qualification.dto.QualificationResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class LeadQualificationService {
    private final int hotThreshold;
    private final int warmThreshold;

    public LeadQualificationService(
            @Value("${app.qualification.hot-threshold:75}") int hotThreshold,
            @Value("${app.qualification.warm-threshold:45}") int warmThreshold
    ) {
        this.hotThreshold = hotThreshold;
        this.warmThreshold = warmThreshold;
    }

    public QualificationResult qualify(Lead lead) {
        String message = lead.getMessage().toLowerCase(Locale.ROOT);
        List<String> reasons = new ArrayList<>();
        int score = 20;

        if (message.contains("automation")) {
            score += 25;
            reasons.add("automation_intent");
        }
        if (message.contains("crm")) {
            score += 20;
            reasons.add("crm_relevance");
        }
        if (message.contains("pricing") || message.contains("budget")) {
            score += 15;
            reasons.add("commercial_signal");
        }
        if (lead.getEmail().contains("@") && !lead.getEmail().endsWith("@gmail.com")) {
            score += 10;
            reasons.add("business_email_signal");
        }
        if (lead.getCountry().equalsIgnoreCase("Canada") || lead.getCountry().equalsIgnoreCase("United States")) {
            score += 10;
            reasons.add("target_market_signal");
        }

        score = Math.min(score, 100);
        QualificationTier tier = score >= hotThreshold ? QualificationTier.HOT : score >= warmThreshold ? QualificationTier.WARM : QualificationTier.COLD;
        return new QualificationResult(score, tier, reasons);
    }
}
