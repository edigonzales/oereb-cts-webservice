package ch.so.agi.oereb.cts.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.so.agi.oereb.cts.ServiceProperties;
import ch.so.agi.oereb.cts.entity.CheckResult;
import ch.so.agi.oereb.cts.entity.ProbeResult;
import ch.so.agi.oereb.cts.GetCapabilitiesWrapper;
import ch.so.agi.oereb.cts.GetEGRIDWrapper;
import ch.so.agi.oereb.cts.GetExtractByIdWrapper;
import ch.so.agi.oereb.cts.GetVersionsWrapper;
import ch.so.agi.oereb.cts.Result;
import ch.so.agi.oereb.cts.repository.ProbeResultRepository;

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
    
    /* Etwas über Spring Transaction Management gelernt...
     * Funktioniert nur bei public-Methoden (wegen Proxies)
     * und via external calls.
     * Andere Lösungen: https://medium.com/javarevisited/spring-transactional-mistakes-everyone-did-31418e5a6d6b
     */
    
    /*
    modelMapper.typeMap(Result.class, ProbeResult.class).addMappings(mapper -> {
        mapper.map(src -> src.getResults(), ProbeResult::setCheckResults);
    });
    */

    // xquery db Idee: 1 XML für einen serviceEndpoint, d.h. beide results zuerst
    // zusammenfügen, dann XML.
            
    @Transactional
    public void validate(String identifier, String serviceEndpoint, Map<String,String> service) {
        probeResultRepository.deleteByIdentifier(identifier);
        {
            var wrapper = new GetVersionsWrapper();
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
            var wrapper = new GetCapabilitiesWrapper();
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
}
