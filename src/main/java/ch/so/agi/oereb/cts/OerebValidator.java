package ch.so.agi.oereb.cts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OerebValidator {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    EndpointProperties endpointProperties;

    //private HashMap<String,List<Result>> results = new HashMap<String,List<Result>>();
    private List<Result> results = new ArrayList<Result>();
    
    public void validate() {
        
        for(Map<String,String> endpoint : endpointProperties.getEndpoints()) {            
            //var results = new ArrayList<Result>();
            

            try {
                {
                    var wrapper = new GetEGRIDWrapper();
                    var probeResults = wrapper.run(endpoint.get("SERVICE_ENDPOINT"), endpoint);
                    results.addAll(probeResults);
                    
                    log.info(probeResults.toString());
                    
                    // Repository:
                    // - Schauen, dass erst "kopiert" wird, wenn alle durch sind.
                    
                    
                    
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
    }

    public List<Result> getResults() {
        return results;
    }

//    public void setResults(HashMap<String, List<Result>> results) {
//        this.results = results;
//    }
}
