package ch.so.agi.oereb.cts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.so.agi.oereb.cts.ServiceProperties;
import ch.so.agi.oereb.cts.GetEGRIDWrapper;
import ch.so.agi.oereb.cts.Result;
import ch.so.agi.oereb.cts.repository.ResultRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OerebValidatorService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ServiceProperties serviceProperties;

    @Autowired
    ResultRepository resultRepository;
    
    //private HashMap<String,List<Result>> results = new HashMap<String,List<Result>>();
    private List<Result> results = new ArrayList<Result>();
    
    public void validate() {
        
        for(Map<String,String> service : serviceProperties.getServices()) {            
            //var results = new ArrayList<Result>();
            

            try {
                {
                    var wrapper = new GetEGRIDWrapper();
                    var probeResults = wrapper.run(service.get("SERVICE_ENDPOINT"), service);
                    results.addAll(probeResults);
                    
                    log.info(probeResults.toString());
                    
                    resultRepository.save(probeResults);
                    
//                    System.out.println(resultRepository.findByIdentifier("so"));
                    
                    
                }
            } catch (IOException e) {
                throw new IllegalArgumentException(e.getMessage());
            }
        }
        
        try {
            System.out.println(resultRepository.findAll());
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }

    public List<Result> getResults() {
        return results;
    }

//    public void setResults(HashMap<String, List<Result>> results) {
//        this.results = results;
//    }
}
