package ch.so.agi.oereb.cts.dto;

import java.net.URI;
import java.time.Instant;
import java.util.List;

public class ProbeResultDTO {
    private Long id;
    
    private List<CheckResultDTO> checkResults;
    
    private String identifier;
    
    private String className; 
    
    private String description;

    private URI serviceEndpoint;

    private boolean success;
            
    private Instant startTime;

    private Instant endTime;

    private double processingTimeSecs;
    
    private String resultFileLocation;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<CheckResultDTO> getCheckResults() {
        return checkResults;
    }

    public void setCheckResults(List<CheckResultDTO> checkResults) {
        this.checkResults = checkResults;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public URI getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(URI serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public double getProcessingTimeSecs() {
        return processingTimeSecs;
    }

    public void setProcessingTimeSecs(double processingTimeSecs) {
        this.processingTimeSecs = processingTimeSecs;
    }

    public String getResultFileLocation() {
        return resultFileLocation;
    }

    public void setResultFileLocation(String resultFileLocation) {
        this.resultFileLocation = resultFileLocation;
    }
}
