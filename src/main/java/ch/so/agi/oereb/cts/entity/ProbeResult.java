package ch.so.agi.oereb.cts.entity;

import java.net.URI;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "probe_result")
public class ProbeResult {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    @OneToMany(
            mappedBy = "probeResult",
            cascade = CascadeType.ALL,
            orphanRemoval = true
        )
    private List<CheckResult> checkResults = new ArrayList<>();
    
    private String identifier;
    
    @Column(name = "class_name")
    private String className; 
    
    private String description;

    @Column(name = "service_endpoint")
    private String serviceEndpoint;

    private boolean success;
    
    private String message;

    private URI request;
    
    @Column(name = "status_code")
    private Integer statusCode;
    
    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Column(name = "processing_time_secs")
    private double processingTimeSecs;
    
    @Column(name = "result_file_location")
    private String resultFileLocation;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public List<CheckResult> getCheckResults() {
        return checkResults;
    }

    public void setCheckResults(List<CheckResult> checkResults) {
        this.checkResults = checkResults;
    }
    
    public void addCheckResult(CheckResult checkResult) {
        checkResults.add(checkResult);
        checkResult.setProbeResult(this);
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

    public String getServiceEndpoint() {
        return serviceEndpoint;
    }

    public void setServiceEndpoint(String serviceEndpoint) {
        this.serviceEndpoint = serviceEndpoint;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public URI getRequest() {
        return request;
    }

    public void setRequest(URI request) {
        this.request = request;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
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
