package ch.so.agi.oereb.cts;

import java.util.List;

public class ProbeResult {
    private String request;
    private List<CheckResult> checkResults;
    
    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public List<CheckResult> getCheckResults() {
        return checkResults;
    }

    public void setCheckResults(List<CheckResult> checkResults) {
        this.checkResults = checkResults;
    }
    
    
}
