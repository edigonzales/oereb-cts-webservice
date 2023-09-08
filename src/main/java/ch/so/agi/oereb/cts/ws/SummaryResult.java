package ch.so.agi.oereb.cts.ws;

import java.time.LocalDateTime;

public record SummaryResult(
        String identifier, 
        String serviceEndpoint, 
        boolean successVersions, 
        boolean successCapabilities, 
        boolean successEgrid, 
        boolean successExtract,
        LocalDateTime testSuiteTime,
        LocalDateTime startTime
        ) {}
