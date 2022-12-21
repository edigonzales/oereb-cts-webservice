package ch.so.agi.oereb.cts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ch.so.agi.oereb.cts.ServiceProperties;
import ch.so.agi.oereb.cts.entity.CheckResult;
import ch.so.agi.oereb.cts.entity.ProbeResult;
import ch.so.agi.oereb.cts.GetEGRIDWrapper;
import ch.so.agi.oereb.cts.GetExtractByIdWrapper;
import ch.so.agi.oereb.cts.Result;
import ch.so.agi.oereb.cts.repository.CheckResultRepository;
import ch.so.agi.oereb.cts.repository.ProbeResultRepository;
import jakarta.transaction.Transactional;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OerebValidatorService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ServiceProperties serviceProperties;

    @Autowired
    ProbeResultRepository probeResultRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private List<Result> results = new ArrayList<Result>();
    
    public void validate() {
        
        // TODO ggf als Bean?
        // Es würde auch Result::getResults funktionieren.
        // Aber automatisch Mappen geht nicht, das sonst der FK fehlt (?)
        /*
        modelMapper.typeMap(Result.class, ProbeResult.class).addMappings(mapper -> {
            mapper.map(src -> src.getResults(), ProbeResult::setCheckResults);
        });
        */

        // TODO: 1 XML für einen serviceEndpoint, d.h. beide results zuerst
        // zusammenfügen, dann XML.
        
        for(Map<String,String> service : serviceProperties.getServices()) {
            String identifier = service.get("identifier");
            String serviceEndpoint = service.get("SERVICE_ENDPOINT");
            this.validateService(identifier, serviceEndpoint, service);
        }
    }
    
    @Transactional
    private void validateService(String identifier, String serviceEndpoint, Map<String,String> service) {
        probeResultRepository.deleteByIdentifier(identifier);

        {
            var wrapper = new GetEGRIDWrapper();
            var results = wrapper.run(serviceEndpoint, service);
                            
            for (Result pResult : results) {
                ProbeResult probeResult = modelMapper.map(pResult, ProbeResult.class);    
                
                for (Result cResult : pResult.getResults()) {
                    CheckResult checkResult = modelMapper.map(cResult, CheckResult.class);
                    probeResult.addCheckResult(checkResult);
                }
                
                probeResultRepository.save(probeResult);
            }
        }
        {
            var wrapper = new GetExtractByIdWrapper();
            var results = wrapper.run(serviceEndpoint, service);

            for (Result pResult : results) {
                ProbeResult probeResult = modelMapper.map(pResult, ProbeResult.class);    
                
                for (Result cResult : pResult.getResults()) {
                    CheckResult checkResult = modelMapper.map(cResult, CheckResult.class);
                    probeResult.addCheckResult(checkResult);
                }
                
                probeResultRepository.save(probeResult);
            }
        }
    }

    public List<Result> getResults() {
        return results;
    }
}
