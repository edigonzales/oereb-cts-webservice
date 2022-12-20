package ch.so.agi.oereb.cts.dto;

import java.time.Instant;

public record ProbeSummaryDTO (String identifier, String serviceEndpoint, Boolean successEgrid, Boolean successExtract, Instant endTime) {}
