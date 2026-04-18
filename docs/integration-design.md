# Integration Design

The integration layer uses adapter boundaries so external systems can be replaced without changing the core lead workflow.

## Current adapters

- `CrmSyncAdapter`
- `NotificationAdapter`

Both are deterministic local adapters. They avoid external credentials and make the project safe for portfolio review.

## Production concerns

A production implementation should add:

- retries with backoff
- dead-letter queues
- circuit breakers
- OAuth or API key management through Azure Key Vault
- idempotent external writes
- structured error handling
- audit logs for every external side effect

## Why this matters

CRM automation often becomes fragile when integrations are implemented directly in workflow code. Adapter boundaries keep business logic separate from external API details.
