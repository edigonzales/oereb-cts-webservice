package ch.so.agi.oereb.cts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import ch.so.agi.oereb.cts.dto.ProbeResultDTO;
import ch.so.agi.oereb.cts.dto.ProbeSummaryDTO;
import ch.so.agi.oereb.cts.entity.ProbeResult;
import ch.so.agi.oereb.cts.repository.ProbeResultRepository;
import ch.so.agi.oereb.cts.service.OerebValidatorService;
import jakarta.persistence.Tuple;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.net.URI;
import java.time.Instant;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//@RestController
@Controller
public class MainController {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ProbeResultRepository probeResultRepository;

    private ModelMapper modelMapper = new ModelMapper();

    @GetMapping("/")
    public String show(Model model) {        
//        List<ProbeResult> probeResults = probeResultRepository.findAll();

        // Weil LAZY werden mit findAll zuerst nur die ProbeResults requestet.
        // Erst beim Mappen werden auch die jeweiligen CheckResults abgefragt.
        // Diese will ich mit in der Übersicht gar nicht. Entweder beim 
        // ModelMapper abstreifen oder eine eigene Query im Respository,
        // was sich aber es garstig herausstellt: Was funktioniert hat, ist
        // wenn man alle Attribute ausschreibt als native Query.
//        modelMapper.typeMap(ProbeResult.class, ProbeResultDTO.class).addMappings(mapper -> mapper.skip(ProbeResultDTO::setCheckResults));
//        
//        List<ProbeResultDTO> probeResultsDTO = probeResults
//                .stream()
//                .map(probeResult -> modelMapper.map(probeResult, ProbeResultDTO.class))
//                .collect(Collectors.toList());

        
        List<Tuple> probeSummaryList = probeResultRepository.getResultSummary();
        
        List<ProbeSummaryDTO> probeSummaryDTOList = probeSummaryList.stream()
            .map(t -> new ProbeSummaryDTO(
                    t.get(0, String.class),
                    t.get(1, String.class),
                    t.get(2, Boolean.class),
                    t.get(3, Boolean.class),
                    t.get(4, java.time.OffsetDateTime.class).toInstant()
                    ))
            .collect(Collectors.toList());
        
        System.out.println(probeSummaryDTOList);

        model.addAttribute("probesSummary", probeSummaryDTOList);
        return "gui";
    } 
    
    @GetMapping("/details/{probe}/{identifier}")
    public String showDetails(@PathVariable String probe, @PathVariable String identifier, Model model) {
        if (!probe.equalsIgnoreCase("getegrid") && !probe.equalsIgnoreCase("extract")) {
            return null; // TODO
        }
        
        String className = "ch.so.agi.oereb.cts.GetEGRIDProbe";
        if (probe.equalsIgnoreCase("extract")) {
            className = "ch.so.agi.oereb.cts.GetExtractByIdProbe";
        }
        
        List<ProbeResult> probeResultList = probeResultRepository.findByIdentifierAndClassName(identifier, className);
        List<ProbeResultDTO> probeResultDTOList = probeResultList.stream()
                .map(probeResult -> modelMapper.map(probeResult, ProbeResultDTO.class)).collect(Collectors.toList());
        
        System.out.println(probeResultDTOList.get(0).getCheckResults().size());
        
        model.addAttribute("probesResults", probeResultDTOList);
        return "details";
    }


}
