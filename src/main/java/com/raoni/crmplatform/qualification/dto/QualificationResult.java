package com.raoni.crmplatform.qualification.dto;

import com.raoni.crmplatform.leads.domain.QualificationTier;

import java.util.List;

public record QualificationResult(
        int score,
        QualificationTier tier,
        List<String> reasons
) {
}
