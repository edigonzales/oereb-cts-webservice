package ch.so.agi.oereb.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ch.so.agi.oereb.cts.ServiceProperties;
import ch.so.agi.oereb.cts.service.CtsService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.time.Instant;

//import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RestController
@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Value("${app.workDirectory}")
    private String workDirectory;

    @Value("${app.folderPrefix}")
    private String folderPrefix;

    @Autowired
    ServiceProperties serviceProperties;

//    @Autowired
//    ProbeResultRepository probeResultRepository;

    @Autowired
    CtsService ctsService;
    
//    private ModelMapper modelMapper = new ModelMapper();
    
    @GetMapping("/ping")
    public ResponseEntity<String> ping() {
        return new ResponseEntity<String>("oereb-cts-webservice", HttpStatus.OK);
    }

//    @GetMapping("/")
//    public String show(Model model) {        
////        List<ProbeResult> probeResults = probeResultRepository.findAll();
//
//        // Weil LAZY werden mit findAll zuerst nur die ProbeResults requestet.
//        // Erst beim Mappen werden auch die jeweiligen CheckResults abgefragt.
//        // Diese will ich mit in der Übersicht gar nicht. Entweder beim 
//        // ModelMapper abstreifen oder eine eigene Query im Respository,
//        // was sich aber es garstig herausstellt: Was funktioniert hat, ist
//        // wenn man alle Attribute ausschreibt als native Query.
////        modelMapper.typeMap(ProbeResult.class, ProbeResultDTO.class).addMappings(mapper -> mapper.skip(ProbeResultDTO::setCheckResults));
////        
////        List<ProbeResultDTO> probeResultsDTO = probeResults
////                .stream()
////                .map(probeResult -> modelMapper.map(probeResult, ProbeResultDTO.class))
////                .collect(Collectors.toList());
//
//        
////        List<Tuple> probeSummaryList = probeResultRepository.getResultSummary();
////
////        List<ProbeSummaryDTO> probeSummaryDTOList = probeSummaryList.stream()
////            .map(t -> new ProbeSummaryDTO(
////                    t.get(0, String.class),
////                    t.get(1, String.class),
////                    t.get(2, Boolean.class),
////                    t.get(3, Boolean.class),
////                    t.get(4, Boolean.class),
////                    t.get(5, Boolean.class),
////                    t.get(6, java.time.OffsetDateTime.class).toInstant()
////                    ))
////            .collect(Collectors.toList());
////
////        model.addAttribute("probesSummary", probeSummaryDTOList);
//        return "gui";
//    } 
    
//    @GetMapping("/details/{probe}/{identifier}")
//    public String showDetails(@PathVariable String probe, @PathVariable String identifier, Model model) {
////        if (!probe.equalsIgnoreCase("versions") && !probe.equalsIgnoreCase("capabilities") && !probe.equalsIgnoreCase("getegrid") && !probe.equalsIgnoreCase("extract")) {
////            return null; // TODO
////        }
////        
////        String className = "ch.so.agi.oereb.cts.GetVersionsProbe";
////        if (probe.equalsIgnoreCase("capabilities")) {
////            className = "ch.so.agi.oereb.cts.GetCapabilitiesProbe";
////        } else if (probe.equalsIgnoreCase("getegrid")) {
////            className = "ch.so.agi.oereb.cts.GetEGRIDProbe";
////        } else if (probe.equalsIgnoreCase("extract")) {
////            className = "ch.so.agi.oereb.cts.GetExtractByIdProbe";
////        }
////        
////        List<ProbeResult> probeResultList = probeResultRepository.findByIdentifierAndClassName(identifier, className);
////        List<ProbeResultDTO> probeResultDTOList = probeResultList.stream()
////                .map(probeResult -> modelMapper.map(probeResult, ProbeResultDTO.class)).collect(Collectors.toList());
////                
////        model.addAttribute("probesResults", probeResultDTOList);
//        return "details";
//    }

    @Scheduled(cron="${app.checkCronExpression}")
    //@Scheduled(cron="0 */4 * * * *")
    private void checkRepos() throws IOException {
        log.info("Validating by scheduler.");
        ctsService.validate();
    }
    
    @Scheduled(cron="0 0/6 * * * *")
    private void cleanUp() {    
        log.debug("Deleting files from previous validation runs...");
        // workDirectory und folderPrefix werden in den Properties gesteuert und
        // und müssen gleich sein wie in der cts library.
        // Falls sie nicht identisch sind, müssen sie entweder in den Properties
        // separat behandelt werden oder hier hardcodiert werden.
        File[] tmpDirs = Paths.get(workDirectory).toFile().listFiles();
        if(tmpDirs!=null) {
            for (java.io.File tmpDir : tmpDirs) {
                if (tmpDir.getName().startsWith(folderPrefix)) {
                    try {
                        FileTime creationTime = (FileTime) Files.getAttribute(Paths.get(tmpDir.getAbsolutePath()), "creationTime");                    
                        Instant now = Instant.now();
                        
                        long fileAge = now.getEpochSecond() - creationTime.toInstant().getEpochSecond();
                        if (fileAge > 60*60*4) {
                            log.info("deleting {}", tmpDir.getAbsolutePath());
                            FileSystemUtils.deleteRecursively(tmpDir);
                        }
                    } catch (IOException e) {
                        throw new IllegalStateException(e);
                    }
                }
            }
        }
    }
}
