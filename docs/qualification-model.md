# Qualification Model

The current qualification model is deterministic and transparent. It is designed to demonstrate how AI-assisted scoring can be wrapped in clear business rules before introducing an external LLM.

## Signals

The scoring model considers:

- automation intent
- CRM relevance
- pricing or budget signal
- business email signal
- target market signal

## Tiers

```text
HOT  -> score >= 75
WARM -> score >= 45
COLD -> score < 45
```

## Production direction

A future version could add:

- LLM-assisted summarization
- historical conversion data
- enrichment APIs
- calibration datasets
- explainability reports
- drift monitoring

The deterministic version is useful because every score can be explained and tested.
