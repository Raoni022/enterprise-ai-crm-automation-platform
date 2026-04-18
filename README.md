# Enterprise AI CRM Automation Platform

A Java 21 and Spring Boot 3 project demonstrating enterprise CRM automation, event-driven lead processing, AI-assisted qualification, integration boundaries, auditability, and cloud-ready architecture.

## Why this project exists

Enterprise automation projects often fail when CRM workflows become a pile of scripts, webhooks, and no-code automations without clear ownership, observability, validation, or retry strategy.

This project demonstrates how to model CRM automation as a backend platform with explicit APIs, domain events, qualification rules, integration adapters, audit logs, and production-oriented boundaries.

## What this project demonstrates

- Java 21 and Spring Boot 3 backend design
- Modular architecture inspired by microservices
- Event-driven lead intake and qualification
- AI-assisted lead scoring with deterministic local logic
- CRM sync adapter boundary
- Notification adapter boundary
- Idempotency for lead intake
- Audit logging and traceability
- CI/CD with GitHub Actions
- Azure production mapping with Service Bus, Functions, Key Vault, and Application Insights

## Architecture

```text
client/webhook
  -> Lead Intake API
      -> idempotency check
      -> lead validation
      -> enrichment service
      -> qualification service
      -> CRM sync adapter
      -> notification adapter
      -> audit log
      -> domain event publisher
```

The current implementation is a modular monolith with in-memory storage. The module boundaries are designed to evolve into microservices later.

## Main endpoints

```http
POST /api/leads
GET /api/leads/{id}
GET /api/leads
POST /api/leads/{id}/qualify
GET /api/integrations/status
```

## Quick start

```bash
mvn test
mvn spring-boot:run
```

Swagger UI:

```text
http://localhost:8080/swagger-ui.html
```

## Example request

```bash
curl -X POST http://localhost:8080/api/leads \
  -H "Content-Type: application/json" \
  -H "Idempotency-Key: demo-lead-001" \
  -d '{
    "fullName": "Alex Morgan",
    "email": "alex@example.com",
    "company": "Northstar Logistics",
    "country": "Canada",
    "source": "website",
    "message": "We need CRM automation and AI lead qualification for our sales team."
  }'
```

## Azure production mapping

| Local component | Azure production mapping |
|---|---|
| Spring Boot API | AKS or Azure App Service |
| Domain events | Azure Service Bus |
| Background enrichment | Azure Functions |
| Secrets | Azure Key Vault |
| Observability | Azure Monitor / Application Insights |
| Persistent storage | Azure Database for PostgreSQL |
| CI/CD | GitHub Actions |

This repository does not claim to be deployed to Azure. It demonstrates a deployment-ready architecture direction.

## Trade-offs

- Storage is in-memory for local simplicity.
- AI qualification is deterministic and transparent instead of calling a paid LLM.
- Integration adapters are mocked to avoid external credentials.
- The architecture is intentionally easy to split into services later.

## Author

Raoni Medeiros  
AI Automation & Systems Engineer
