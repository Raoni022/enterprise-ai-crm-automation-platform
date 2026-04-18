# Architecture

This project models enterprise CRM automation as a backend platform instead of a collection of scripts or no-code workflows.

## Modules

```text
leads          -> intake API, idempotency, lead workflow orchestration
enrichment     -> deterministic enrichment boundary
qualification  -> AI-assisted scoring and lead tier classification
integrations   -> CRM and notification adapter boundaries
audit          -> audit logging boundary
messaging      -> event publishing boundary
```

## Flow

```text
POST /api/leads
  -> validate request
  -> enforce idempotency key
  -> create lead
  -> enrich lead
  -> store lead
  -> record audit event
  -> publish lead.created event

POST /api/leads/{id}/qualify
  -> load lead
  -> score qualification signals
  -> classify HOT/WARM/COLD
  -> sync to CRM adapter
  -> draft notification
  -> record audit event
  -> publish lead.qualified event
```

## Why modular monolith first

The system uses a modular monolith to keep local execution simple while preserving service boundaries. In production, these modules can evolve into independently deployable services or workers.

## Production evolution

- Lead intake API -> Spring Boot service on AKS or Azure App Service
- Lead events -> Azure Service Bus
- Enrichment and scoring workers -> Azure Functions or Container Apps Jobs
- CRM credentials -> Azure Key Vault
- Observability -> Azure Monitor and Application Insights
- Storage -> Azure Database for PostgreSQL
