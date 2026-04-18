package com.raoni.crmplatform.leads.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateLeadRequest(
        @NotBlank String fullName,
        @Email @NotBlank String email,
        @NotBlank String company,
        @NotBlank String country,
        @NotBlank String source,
        @NotBlank String message
) {
}
